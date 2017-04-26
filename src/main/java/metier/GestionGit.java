package metier;

import controller.GestionController;
import model.Commit;
import model.Dossier;
import model.Fichier;
import model.Projet;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.commons.FileUtils;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GestionGit {
    /**
     * TODO : changer le chemin du répertoire qui contient les dépôts git, pour la vm
     */
    public static String localPath;
    private GestionParsing gestionParsing;
    protected static final Logger LOGGER = LoggerFactory.getLogger(GestionGit.class);

    public GestionGit() {
        File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/git");
        if(!file.exists())
        {
            file.mkdirs();
        }

        localPath = file.getAbsolutePath() + "/";
    }

    /**
     * Permet de créer le path du repo d'un projet
     * @param projet - le projet
     * @return
     */
    public String getPathProjet(Projet projet) {
        try {
            if (projet == null || projet.getName().isEmpty()) {
                return null;
            }

            return localPath + projet.getName();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer le path du dossier git
     * @return - path du dossier git
     */
    public String getPathRootGit()
    {
        try
        {
            return localPath;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Crée un nouveau dépôt git pour l'utilisateur, appelé quand on crée un projet
     *
     * @param projet - le projet cible
     * @return true si réussite, false sinon
     */
    public boolean gitNew(Projet projet) {
        try {
            if (projet == null || projet.getName() == null || projet.getName().isEmpty()) {
                return false;
            }

            String path = getPathProjet(projet);
            File file = new File(path);

            //On regarde si le dossier existe déjà
            if (file.exists()) {
                return false;
            }

            //On regarde si on arrive à créer le dossier
            if (!mkdir(file)) {
                return false;
            }

            //Création du repo Git
            Git git = Git.init().setDirectory(file).call();
            return git != null;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de créer un dossier dans le repo git
     *
     * @param projet               - le projet
     * @param dossier              - le dossier à créer
     * @param cheminRelatifFichier - le chemin relatif au projet du dossier
     * @return
     */
    public boolean createDossier(Projet projet, Dossier dossier, String cheminRelatifFichier) {
        try {
            if (projet == null || projet.getName().isEmpty() || cheminRelatifFichier == null) {
                return false;
            }

            if (cheminRelatifFichier.isEmpty() || dossier == null) {
                return false;
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }

            //Création de l'objet File du fichier du projet à ajouter
            File fileDossier = new File(fileProjet.getPath() + "/" + cheminRelatifFichier);
            if (fileDossier.exists()) {
                return true;
            }

            //Création du fichier
            if (!mkdir(fileDossier)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de créer un fichier dans un repo
     *
     * @param projet               - le projet
     * @param fichier              - le fichier
     * @param cheminRelatifFichier - le chemin relatif du fichier dans le repo git
     * @return
     */
    public boolean createFichier(Projet projet, Fichier fichier, String cheminRelatifFichier) {
        try {
            if (projet == null || projet.getName().isEmpty() || cheminRelatifFichier == null) {
                return false;
            }

            if (cheminRelatifFichier.isEmpty() || fichier == null) {
                return false;
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }

            //Création de l'objet File du fichier du projet à ajouter
            File fileFichier = new File(fileProjet.getPath() + "/" + cheminRelatifFichier);
            if (fileFichier.exists()) {
                return false;
            }

            //Création du fichier
            if (!touch(fileFichier)) {
                return false;
            }

            //Ajout du fichier dans git
            if (!add(git, cheminRelatifFichier)) {
                return false;
            }

            //On commit
            if (!commit(git, "Création du fichier : " + fichier.getNomComplet())) {
                return false;
            }

            //On push
            if (!push(git)) {
                return false;
            }

            //On ferme git
            closeGit(git);

            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de récupérer le contenu d'un fichier
     * @param path - le path relatif au repo du fichier
     * @return
     */
    public String getContentFile(String path)
    {
        try
        {
            File file = new File(localPath + path);
            return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de modifier le conteny d'un fichier
     * @param path - le path relatif au repo du fichier
     * @param newContent - le nouveau contenu
     * @return
     */
    public boolean updateFichier(String path, String newContent, int idFichier) {
        try {
            if (path == null || path.isEmpty() || newContent == null || idFichier < 1) {
                return false;
            }

            File file = new File(localPath + path);
            if (!file.exists()) {
                return false;
            }

            try (FileWriter fooWriter = new FileWriter(file, false)) {
                fooWriter.write(newContent);
                fooWriter.close();
            }


            //On récup le nom du projet via le path
            int indexOf = path.indexOf("/");
            String nameProjet = path.substring(0, indexOf);
            String cheminRelatifFichier = path.substring(indexOf + 1, path.length());

            //Création de l'objet File du répository du projet
            File fileProjet = new File(localPath + nameProjet);
            if (!fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }


            //Ajout du fichier dans git
            if (!add(git, cheminRelatifFichier)) {
                return false;
            }

            //On commit
            if (!commit(git, "Update du fichier : ")) {
                return false;
            }

            //On push
            if (!push(git)) {
                return false;
            }

            //On ferme git
            closeGit(git);

            //On parse le fichier
            EntityManager em = GestionController.createEntityManager();
            gestionParsing = new GestionParsing(em);
            gestionParsing.parseFichier(file, idFichier);
            em.close();
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet d'obtenir la liste de tous les fichiers du repo
     * @param projet - le projet
     * @return
     */
    public List<String> getArborescenceRepo(Projet projet) {
        try {
            if (projet == null || projet.getName().isEmpty())
                return new ArrayList<>();

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return new ArrayList<>();
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return new ArrayList<>();
            }

            //On récup le repo
            Repository repository = git.getRepository();

            //On récup la ref du dernier commit
            ObjectId lastCommitId = getLastIdCommit(git);

            ArrayList<String> arborescence = new ArrayList<>();

            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(lastCommitId);
                RevTree tree = commit.getTree();

                try (TreeWalk treeWalk = new TreeWalk(repository)) {
                    treeWalk.addTree(tree);
                    treeWalk.setRecursive(true);
                    while (treeWalk.next()) {
                        arborescence.add(treeWalk.getPathString());
                    }
                }
            }

            return arborescence;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste des chemins absoluts (à partir du dossier git) des fichiers d'un projet
     * @param projet - le projet
     * @return - la liste des chemins
     */
    public List<String> getArborescenceAbsolute(Projet projet)
    {
        try
        {
            if (projet == null) {
                return new ArrayList<>();
            }

            List<String> arboRelatif = getArborescenceRepo(projet);
            String localPathGit = getPathRootGit();

            List<String> response = new ArrayList<>();

            for(String s : arboRelatif)
            {
                response.add(localPathGit + projet.getName() + "/" + s);
            }

            return response;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet d'ajouter tous les fichiers dans l'index du repo
     * @param projet - le projet
     * @return - succés si add ok
     */
    public boolean addAllFilesRepo(Projet projet)
    {
        try
        {
            if (projet == null) {
                return false;
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }

            git.add().addFilepattern(".").call();

            if (commit(git, "add all file")) {
                return false;
            }

            return push(git);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet d'ajouter dans le fichier d'index de Git le dossier ou fichier voulu
     * @param git           - l'objet git
     * @param cheminRelatif - le chemin relatif au repo git de l'élément
     * @return
     */
    public boolean add(Git git, String cheminRelatif) {
        try {
            if (git == null || cheminRelatif == null || cheminRelatif.isEmpty()) {
                return false;
            }


            return git.add().addFilepattern(cheminRelatif).call() != null;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet d'effectuer un Push
     *
     * @param git - l'objet Git
     * @return
     */
    public boolean push(Git git) {
        try {
            if (git == null) {
                return false;
            }

            return git.push() != null;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de faire un commit
     *
     * @param git     - l'objet Git
     * @param message - le message du commit
     * @return true si réussite, false sinon
     */
    public boolean commit(Git git, String message) {
        try {
            if (git == null || message == null || message.isEmpty()) {
                return false;
            }

            return git.commit().setMessage(message).call() != null;

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de supprimer un repo lié à un projet
     * @return true si réussite, false sinon
     */
    public boolean remove(Projet projet) {
        try
        {
            if (projet == null || projet.getName().isEmpty())
                return false;

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            FileUtils.deleteDirectory(fileProjet);
            return true;
        }
        catch (Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * @param idUser
     * @param idProject
     * @return true si réussite, false sinon
     */
    public boolean status(int idUser, int idProject) {
        try {
            try (Git git = Git.open(new File(localPath + idUser + "/" + idProject + "/.git"))) {
                git.status().call();
                git.close();
                return true;
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return false;
        } catch (GitAPIException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de récupérer tous les commits
     *
     * @param projet - le projet du repo
     * @return
     */
    public ArrayList<Commit> getCommits(Projet projet) {
        try {
            if (projet == null) {
                return new ArrayList<>();
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return new ArrayList<>();
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return new ArrayList<>();
            }

            //On récup les commits
            ArrayList<Commit> reponse = new ArrayList<>();

            for (RevCommit rev : git.log().call()) {
                PersonIdent authorIdent = rev.getAuthorIdent();
                reponse.add(new Commit(authorIdent.getName(), rev.getId().getName(), authorIdent.getWhen()));
            }

            return reponse;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste de tous les commits
     *
     * @param git - l'objet git ouvert
     * @return
     */
    public Iterable<RevCommit> getCommits(Git git) {
        try {
            if (git == null) {
                return new ArrayList<>();
            }

            //On récup les commits
            return git.log().call();
            /*for (RevCommit rev : revs) {
                System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() );
            }*/
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de faire un checkout sur un repo
     *
     * @param projet   - le projet
     * @param idCommit - le nom du commit sur lequel faire le checkout
     * @return
     */
    public boolean checkout(Projet projet, String idCommit) {
        try {
            if (projet == null || projet.getName().isEmpty()) {
                return false;
            }

            if (idCommit == null || idCommit.isEmpty()) {
                return false;
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }


            //On récup la liste des commits
            Iterable<RevCommit> revs = getCommits(git);

            //On parcourt les commits pour faire le checkout
            for (RevCommit r : revs) {
                String id = r.getId().getName();
                if (!id.equals(idCommit)) {
                    continue;
                }
                git.checkout().setName(r.getId().getName()).call();

                ObjectId lastIdCommit = getLastIdCommit(git);
                return lastIdCommit.getName().equals(idCommit);
            }

            return false;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet d'ouvrir une connexion Git
     *
     * @param fileProjet - Le file du repo
     * @return
     */
    private Git openGit(File fileProjet) {
        try {
            if (fileProjet == null) {
                return null;
            }

            return Git.open(fileProjet);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de fermer la connexion Git
     *
     * @param git - l'objet Git
     * @return
     */
    public boolean closeGit(Git git) {
        try {
            if (git == null) {
                return false;
            }

            git.close();
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet d'instancier un objet File pour le projet
     *
     * @param projet - le projet
     * @return
     */
    private File createFileProjet(Projet projet) {
        try {
            if (projet == null || projet.getName() == null || projet.getName().isEmpty()) {
                return null;
            }

            return new File(getPathProjet(projet));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de créer un dossier
     *
     * @param file - le dossier
     * @return
     */
    public boolean mkdir(File file) {
        try {
            if (file == null) {
                return false;
            }

            return !file.exists() && file.mkdir();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de créer des dossiers en récursif
     * @param file - les dossier à créer
     * @return - succès
     */
    public boolean mkdirs(File file)
    {
        try
        {
            if (file == null) {
                return false;
            }

            return !file.exists() && file.mkdirs();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de créer un fichier
     *
     * @param file - le fichier
     * @return
     */
    public boolean touch(File file) {
        try {
            if (file == null) {
                return false;
            }

            return !file.exists() && file.createNewFile();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean deleteFile(Projet projet, Fichier fichier) {
        try {
            if (projet == null || fichier == null) {
                return false;
            }

            //Création de l'objet File du répository du projet
            File fileProjet = createFileProjet(projet);
            if (fileProjet == null || !fileProjet.exists()) {
                return false;
            }

            //On instancie un objet Git via le file du projet
            Git git = openGit(fileProjet);
            if (git == null) {
                return false;
            }

            //Création du file du fichier
            String pathFichier = fichier.getCheminComplet();
            if (pathFichier == null || pathFichier.isEmpty()) {
                return false;
            }

            pathFichier += "." + fichier.getExtension();
            File file = new File(localPath + projet.getName() + "/" + pathFichier);
            if (!file.exists()) {
                return false;
            }

            //On supprime le fichier
            if (!file.delete()) {
                return false;
            }

            if(git.rm().addFilepattern(pathFichier).call() == null)
            {
                return false;
            }

            if(!commit(git, "Suppression du fichier"))
            {
                return false;
            }

            if(!push(git))
            {
                return false;
            }

            return true;
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            return false;
        }
    }

    /**
     * Permet d'obtenir l'id du dernier commit d'un repo
     * @param git
     * @return
     * @throws Exception
     */
    private ObjectId getLastIdCommit(Git git) throws Exception {
        try {
            Repository repository = git.getRepository();
            return repository.resolve(Constants.HEAD);
        } catch (IOException e) {
            throw new Exception("error last id commit");
        }
    }
}
