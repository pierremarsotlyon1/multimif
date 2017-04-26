package metier;

import dao.DossierDao;
import dao.FichierDao;
import dao.ProjetDao;
import model.Dossier;
import model.ElementProjet;
import model.Fichier;
import model.Projet;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pierremarsot on 14/10/2016.
 */
public class GestionEditionProjet extends GestionBase {
    private FichierDao fichierDao;
    private DossierDao dossierDao;
    private ProjetDao projetDao;
    private GestionGit gestionGit;

    public GestionEditionProjet(EntityManager em) {
        super(em);
        fichierDao = new FichierDao(em);
        dossierDao = new DossierDao(em);
        projetDao = new ProjetDao(em);
        gestionGit = new GestionGit();
    }

    /**
     * Permet de récupérer l'arborescence du projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - un Dossier contenant l'arborescence du projet
     */
    public Dossier getContenu(int idProjet, int idUser) {
        try {
            if (idProjet < 1 || idUser < 1) {
                return null;
            }

            //On récup le projet
            Projet projet = em.createQuery("SELECT p FROM Projet p INNER JOIN p.users u WHERE u.id = :idUser AND p.id = :idProjet", Projet.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("idUser", idUser)
                    .getSingleResult();
            if (projet == null) {
                return null;
            }
            List<ElementProjet> elementProjets = em.createQuery("SELECT e FROM ElementProjet e JOIN e.projet p WHERE p.id = :idProjet",
                    ElementProjet.class)
                    .setParameter("idProjet", idProjet)
                    .getResultList();

            //Création du dossier root fantôme
            Dossier root = new Dossier(projet.getId(), projet.getName());
            root.setProjet(projet);

            //Création de l'arborescence
            root.getElementProjets().addAll(createArborescenceProjet(elementProjets, null));

            return root;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de remplacer en bdd l'arborescence du projet
     * @param idProjet - id du projet
     * @param arborescence - la liste de tous les fichiers dans le repo Git
     * @param idUser - id de l'user connecté
     * @return
     */
    public boolean replaceArborescence(int idProjet, List<String> arborescence, int idUser) {
        try {
            if (idProjet < 1 || idUser < 1) {
                return false;
            }

            //On récup le projet
            Projet projet = projetDao.findByIdProjet(idProjet);
            if (projet == null) {
                return false;
            }

            //On récup le path du projet dans le dossier git
            String pathProjet = gestionGit.getPathProjet(projet);
            if (pathProjet == null || pathProjet.isEmpty()) {
                return false;
            }

            //On supprime en bdd les fichiers/dossiers
            if (!deleteElementsProjet(projet)) {
                return false;
            }

            //On parcourt la nouvelle arborescence, a chaque parcourt, on re-crée une arborescence pour pouvoir
            // l'ajouter en bdd
            for (String s : arborescence) {
                if (s == null || s.isEmpty()) {
                    continue;
                }

                //Création de l'objet File pour gérer le dossier/fichier
                File file = new File(pathProjet + "/" + s);

                //Liste qui contiendra le fichier et les dossiers à ajouter pour le fichier courant
                ArrayList<ElementProjet> elements = new ArrayList<>();

                //On remonte dans l'arborescence jusqu'au dossier root du projet
                while(file.getAbsolutePath().contains(pathProjet) && !file.getAbsolutePath().equals(pathProjet))
                {
                    if(file.isDirectory())
                    {
                        elements.add(new Dossier(file.getName()));
                    }
                    else
                    {
                        Fichier fichier = new Fichier(file.getName());
                        if(fichier.calculateExtension())
                        {
                            elements.add(fichier);
                        }
                    }
                    file = file.getParentFile();
                }

                //On renverse la liste
                Collections.reverse(elements);

                //On parcourt la liste pour l'ajouter en bdd
                Dossier parent = null;
                while(!elements.isEmpty())
                {
                    ElementProjet element = elements.get(0);

                    if(element instanceof Dossier)
                    {
                        //On cast l'élément
                        Dossier dossier = (Dossier)element;

                        if(parent != null)
                        {
                            //On regarde si le dossier se trouve dans la liste des elements du projet
                            Dossier d = null;
                            for(ElementProjet e : parent.getElementProjets())
                            {
                                if (e instanceof Fichier) {
                                    continue;
                                }
                                if(!e.getName().equals(dossier.getName())) {
                                    continue;
                                }

                                d = (Dossier)e;
                                break;
                            }

                            //On regarde si il faut créer le dossier
                            if(d == null)
                            {
                                dossier.setProjet(projet);
                                dossier.setParent(parent);
                                projet.getElementProjets().add(dossier);
                                parent.getElementProjets().add(dossier);
                                parent = dossier;

                                dossierDao.addDossier(dossier);
                            }
                            else
                            {
                                parent = d;
                            }
                        }
                        else
                        {
                            Dossier d = null;
                            for(ElementProjet e : projet.getElementProjets())
                            {
                                if (e instanceof Fichier) {
                                    continue;
                                }
                                Dossier temp = (Dossier)e;
                                if (temp == null) {
                                    continue;
                                }

                                if (temp.getParent() != null) {
                                    continue;
                                }

                                if(!e.getName().equals(dossier.getName())) {
                                    continue;
                                }

                                d = (Dossier)e;
                                break;
                            }

                            if(d == null)
                            {
                                dossier.setProjet(projet);
                                dossier.setParent(null);
                                projet.getElementProjets().add(dossier);
                                parent = dossier;
                                dossierDao.addDossier(dossier);
                            }
                            else
                            {
                                parent = d;
                            }
                        }
                    }
                    else if(element instanceof Fichier)
                    {
                        Fichier fichier = (Fichier)element;
                        fichier.setProjet(projet);
                        fichier.setParent(parent);


                        if(parent == null)
                        {
                            projet.getElementProjets().add(fichier);
                        }
                        else
                        {
                            parent.getElementProjets().add(fichier);
                        }

                        fichierDao.addFichier(fichier);
                    }

                    elements.remove(0);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Permet de supprimer en bdd les fichiers/dossiers du projet
     * @param projet - le projet cible
     * @return - succés de la suppression
     */
    private boolean deleteElementsProjet(Projet projet) {
        try {
            if (projet == null) {
                return false;
            }

            while(!projet.getElementProjets().isEmpty()) {
                ElementProjet e = projet.getElementProjets().get(0);
                if (e instanceof Dossier) {
                    Dossier dossier = (Dossier) e;
                    if (!deleteElementsDossier(dossier)) {
                        return false;
                    }
                    em.remove(dossier);
                } else if (e instanceof Fichier) {
                    Fichier fichier = (Fichier) e;
                    em.remove(fichier);
                }
                projet.getElementProjets().remove(0);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Permet de supprimer en bdd les fichiers/dossiers du projet
     * @param dossier - le dossier cible
     * @return - succés de la suppression
     */
    private boolean deleteElementsDossier(Dossier dossier) {
        try {
            if (dossier == null) {
                return false;
            }

            while(!dossier.getElementProjets().isEmpty()) {
                ElementProjet e = dossier.getElementProjets().get(0);
                if (e instanceof Dossier) {
                    Dossier d = (Dossier) e;
                    if (!deleteElementsDossier(d)) {
                        return false;
                    }
                    em.remove(dossier);
                } else if (e instanceof Fichier) {
                    Fichier fichier = (Fichier) e;
                    em.remove(fichier);
                }
                dossier.getElementProjets().remove(0);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Permet d'ajouter un fichier au projet
     * @param fichier - le fichier à ajouter
     * @param idDossier - id du dossier qui devra contenir le fichier
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param dossierRoot - est-ce le dossier root
     * @return - le fichier ajouté
     */
    public Fichier addFichier(Fichier fichier, int idDossier, int idProjet, int idUser, boolean dossierRoot) {
        try {
            if (fichier == null) {
                return null;
            }

            if (idDossier < 1 || idProjet < 1 || idUser < 1) {
                return null;
            }

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if (projet == null) {
                return null;
            }

            //On parse l'extension et le nom du fichier
            if (!fichier.calculateExtension()) {
                return null;
            }

            //Création du chemin de base
            String chemin = "";

            //On récup le dossier parent
            Dossier parent = dossierDao.get(idDossier, idProjet);
            if (parent != null && !dossierRoot) {
                //On remonte jusqu'à la racine pour générer le chemin
                Dossier temp = parent;
                String response = "";
                while (temp != null) {
                    String nomDossier = temp.getName();
                    if (nomDossier == null || nomDossier.isEmpty()) {
                        return null;
                    }

                    temp = temp.getParent();
                    if (temp == null) {
                        response = nomDossier + response;
                    } else {
                        response = "/" + nomDossier + response;
                    }
                }

                chemin += response;

                //On ajoute le fichier aux éléments du dossier
                parent.getElementProjets().add(fichier);

                //On lie le fichier au dossier
                fichier.setParent(parent);
            }

            if (chemin.isEmpty()) {
                chemin = fichier.getNomComplet();
            } else {
                chemin += "/" + fichier.getNomComplet();
            }

            //On lie le projet au fichier
            fichier.setProjet(projet);

            //On lie le fichier au projet
            projet.getElementProjets().add(fichier);

            fichier = fichierDao.addFichier(fichier);
            if (fichier == null) {
                return null;
            }

            //On crée le fichier dans le dépôt
            if (!gestionGit.createFichier(projet, fichier, chemin)) {
                return null;
            }

            return fichier;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet d'ajouter un dossier au projet
     *
     * @param dossier - le dossier à ajouter
     * @param idDossier - id du dossier parent
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param dossierRoot - est-ce le dossier root du projet
     * @return - le dossier ajouté
     */
    public Dossier addDossier(Dossier dossier, int idDossier, int idProjet, int idUser, boolean dossierRoot) {
        try {
            if (dossier == null) {
                return null;
            }

            if (idDossier < 1 || idProjet < 1 || idUser < 1) {
                return null;
            }

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if (projet == null) {
                return null;
            }

            //Création du chemin de base
            String chemin = "";

            //On récup le dossier parent
            Dossier parent = dossierDao.get(idDossier, idProjet);
            if (parent != null && !dossierRoot) {
                //On remonte jusqu'à la racine pour générer le chemin
                Dossier temp = parent;
                String response = "";
                while (temp != null) {
                    String nomDossier = temp.getName();
                    if (nomDossier == null || nomDossier.isEmpty()) {
                        return null;
                    }

                    temp = temp.getParent();
                    if (temp == null) {
                        response = nomDossier + response;
                    } else {
                        response = "/" + nomDossier + response;
                    }
                }

                chemin += response;

                //On ajoute le fichier aux éléments du dossier
                parent.getElementProjets().add(dossier);

                //On lie le fichier au dossier
                dossier.setParent(parent);
            }

            if (chemin.isEmpty()) {
                chemin = dossier.getName();
            } else {
                chemin += "/" + dossier.getName();
            }

            //On lie le projet au dossier
            dossier.setProjet(projet);

            //On lie le dossier au projet
            projet.getElementProjets().add(dossier);

            dossier = dossierDao.addDossier(dossier);
            if (dossier == null) {
                return null;
            }

            //On crée le dossier dans le dépôt
            if (!gestionGit.createDossier(projet, dossier, chemin)) {
                return null;
            }

            return dossier;
        } catch (Exception e) {
            return null;
        }
    }

    public Fichier getFichier(int idFichier) {
        try {
            return fichierDao.get(idFichier);
        } catch (Exception e) {
            return null;
        }
    }

    public Dossier getDossier(int idDossier, int idProjet) {
        try {
            return dossierDao.get(idDossier, idProjet);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de créer l'arborescence du projet
     * @param elementProjets - les éléments courants
     * @param parent - le dossier parent
     * @return - arborescence
     */
    private List<ElementProjet> createArborescenceProjet(List<ElementProjet> elementProjets, Dossier parent) {
        try {
            if (elementProjets == null || elementProjets.isEmpty()) {
                return new ArrayList<>();
            }

            //On récupère la liste des éléments qui sont dans le dossier parent
            List<ElementProjet> temp = new ArrayList<ElementProjet>();
            for (ElementProjet e : elementProjets) {
                if (e.getParent() != parent) {
                    continue;
                }

                temp.add(e);
            }

            //On remplit les dossiers
            for (ElementProjet e : temp) {
                if (e instanceof Dossier) {
                    Dossier dossier = (Dossier) e;
                    dossier.getElementProjets().addAll(createArborescenceProjet(temp, dossier));
                }
            }

            return temp;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste des fichiers qui peuvent être lancés (ex : un main)
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return List de fichier lançable
     */
    public List<String> getAllMainFichiers(int idProjet,int idUser){
        ArrayList<String> mainFichiers = new ArrayList<>();
        List<Fichier> fichiers = fichierDao.getAllMainFichiers(idProjet,idUser);
        for (Fichier f :
                fichiers) {
            mainFichiers.add(f.getCheminComplet());
        }
        return mainFichiers;
    }

    /**
     * Permet de récupérer la liste des fichiers qui ont une javadoc
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return List des fichiers qui ont une javadoc
     */
    public List<Fichier> getFichiersWithJavaDoc(int idProjet, int idUser)
    {
        try
        {
            if (idProjet < 1 || idUser < 1) {
                return new ArrayList<>();
            }

            return fichierDao.getFichiersWithJavaDoc(idProjet, idUser);
        }
        catch(Exception e)
        {
            return new ArrayList<>();
        }
    }

    /**
     * Permet de supprimer un fichier d'un projet
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès du remove
     */
    public boolean deleteFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            //On récup le fichier
            Fichier fichier = fichierDao.get(idFichier);
            if (fichier == null) {
                return false;
            }

            //On récup le projet
            Projet projet = projetDao.findByIdProjet(idProjet);
            if (projet == null) {
                return false;
            }

            //On supprime le fichier du git
            if (!gestionGit.deleteFile(projet, fichier)) {
                return false;
            }

            //On supprime le fichier de la bdd
            if (!fichierDao.deleteFichier(idProjet, idFichier)) {
                return false;
            }

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de mettre un fichier non executable
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès
     */
    public boolean setUnMainFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            return fichierDao.setUnMainFichier(idProjet, idFichier);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de mettre un fichier executable
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès
     */
    public boolean setMainFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            return fichierDao.setMainFichier(idProjet, idFichier);
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
