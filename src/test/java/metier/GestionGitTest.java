package metier;

import controller.GestionController;
import dao.DossierDao;
import dao.FichierDao;
import dao.UserDao;
import junit.framework.TestCase;
import model.Dossier;
import model.Fichier;
import model.Projet;
import model.User;
import org.eclipse.jgit.api.Git;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zeroturnaround.zip.commons.FileUtils;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class GestionGitTest extends TestCase {

    private GestionGit gestionGit;
    private EntityManager em;
    private Projet projet;
    private User user;
    private UserDao userDao;
    private String localPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/git" + "/"; /* TODO : change for the vm */

    public GestionGitTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionGit = new GestionGit();

        userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        GestionProjet gestionProjet = new GestionProjet(em);
        projet = new Projet();
        projet.setName("projetTest");
        projet = gestionProjet.addProjet(projet, user.getId());
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        gestionGit.remove(projet);
        userDao.rollback();
        em.close();
    }

    @Test
    public void testGetPathProjet() {
        assertEquals("Le chemin du projet est incorrect", localPath+projet.getName(), gestionGit.getPathProjet(projet));
    }

    @Test
    public void testGetPathRootGit() {
        assertEquals("Le chemin racine est incorrect", localPath, gestionGit.getPathRootGit());
    }

    @Test
    public void testGitNew() {
        Projet p = new Projet();
        p.setName("test");
        assertTrue("Le depot git du projet n'a pas ete cree", gestionGit.gitNew(p));
        gestionGit.remove(p);
    }

    @Test
    public void testCreateDossier() {
        Dossier dossier = new Dossier("dossierTest");
        DossierDao dossierDao = new DossierDao(em);
        dossierDao.addDossier(dossier);
        String chemin = "/" + dossier.getName();
        assertTrue("Le dossier n'a pas ete cree dans le depot git", gestionGit.createDossier(projet, dossier, chemin));
    }

    @Test
    public void testCreateFichier() {
        Fichier fichier = new Fichier("fichierTest");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        String chemin = "/" + fichier.getName();
        assertTrue("Le fichier n'a pas ete cree dans le depot git", gestionGit.createFichier(projet, fichier, chemin));
    }

    @Test
    public void testGetContentFile() {

    }

    @Test
    public void testUpdateFichier() {

    }

    @Test
    public void testGetArborescenceRepo() {

    }

    @Test
    public void testGetArborescenceAbsolute() {

    }

    @Test
    public void testAddAllFilesRepo() {

    }

    @Test
    public void testAdd() {
        File fileProjet = new File(gestionGit.getPathProjet(projet));

        Git git = null;
        try {
            git = Git.open(fileProjet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Fichier fichier = new Fichier("fichierTest");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        String chemin = "/" + fichier.getName();
        assertTrue("Le fichier n'a pas ete ajoute a l'index git", gestionGit.add(git, chemin));

        git.close();
    }

    @Test
    public void testPush() {
        File fileProjet = new File(gestionGit.getPathProjet(projet));

        Git git = null;
        try {
            git = Git.open(fileProjet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Fichier fichier = new Fichier("fichierTest");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        String chemin = "/" + fichier.getName();
        gestionGit.add(git, chemin);

        assertTrue("Le push n'a pas fonctionne", gestionGit.push(git));

        git.close();
    }

    @Test
    public void testCommit() {
        File fileProjet = new File(gestionGit.getPathProjet(projet));

        Git git = null;
        try {
            git = Git.open(fileProjet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Fichier fichier = new Fichier("fichierTest");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        String chemin = "/" + fichier.getName();
        gestionGit.add(git, chemin);

        assertTrue("Le commit n'a pas fonctionne", gestionGit.commit(git,"test commit"));

        git.close();
    }

    @Test
    public void testRemove() {
        assertTrue("Le depot git du projet n'a pas ete supprime", gestionGit.remove(projet));
    }

    @Test
    public void testStatus() {

    }

    @Test
    public void testGetCommits() {

    }

    @Test
    public void testCheckout() {

    }

    @Test
    public void testCloseGit() {
        File fileProjet = new File(gestionGit.getPathProjet(projet));

        Git git = null;
        try {
            git = Git.open(fileProjet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue("Git n'a pas pu etre ferme", gestionGit.closeGit(git));
    }

    @Test
    public void testMkdir() {
        File file = new File("dossier");

        assertTrue("Le dossier n'a pas ete cree", gestionGit.mkdir(file));

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTouch() {
        File file = new File("fichier");

        assertTrue("Le fichier n'a pas ete cree", gestionGit.touch(file));
        FileUtils.deleteQuietly(file);
    }

}
