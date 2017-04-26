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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class GestionEditionProjetTest extends TestCase {

    private GestionEditionProjet gestionEditionProjet;
    private UserDao userDao;
    private EntityManager em;
    private User user;
    private Projet projet;

    public GestionEditionProjetTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionEditionProjet = new GestionEditionProjet(em);
        gestionEditionProjet.beginTransaction();

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
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        gestionEditionProjet.rollback();
        em.close();
    }

    @Test
    public void testGetContenu() {
        Dossier dossier = new Dossier("dossierTest");
        DossierDao dossierDao = new DossierDao(em);
        dossierDao.addDossier(dossier);
        gestionEditionProjet.addDossier(dossier, 1, projet.getId(), user.getId(), true);

        Fichier fichier = new Fichier("fichierTest.txt");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        gestionEditionProjet.addFichier(fichier, dossier.getId(), projet.getId(), user.getId(), true);

        Dossier res = gestionEditionProjet.getContenu(projet.getId(), user.getId());
        assertEquals("Le nom du projet est incorrect", projet.getName(), res.getName());
    }

    @Test
    public void testReplaceArborescence() {

    }

    @Test
    public void testAddFichier() {
        Fichier fichier = new Fichier("fichierTest.txt");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        assertEquals("Le fichier n'a pas ete ajoute", fichier.getId(), gestionEditionProjet.addFichier(fichier, 1, projet.getId(), user.getId(), true).getId());
    }

    @Test
    public void testAddDossier() {
        Dossier dossier = new Dossier("dossierTest");
        DossierDao dossierDao = new DossierDao(em);
        dossierDao.addDossier(dossier);
        assertEquals("Le dossier n'a pas ete ajoute", dossier.getId(), gestionEditionProjet.addDossier(dossier, 1, projet.getId(), user.getId(), true).getId());
    }

    @Test
    public void testGetFichier() {
        Fichier fichier = new Fichier("fichierTest.txt");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        gestionEditionProjet.addFichier(fichier, 1, projet.getId(), user.getId(), true);
        assertEquals("Le fichier n'est pas correct", fichier.getId(), gestionEditionProjet.getFichier(fichier.getId()).getId());
    }

    @Test
    public void testGetDossier() {
        Dossier dossier = new Dossier("dossierTest");
        DossierDao dossierDao = new DossierDao(em);
        dossierDao.addDossier(dossier);
        gestionEditionProjet.addDossier(dossier, 1, projet.getId(), user.getId(), true);
        assertEquals("Le dossier n'est pas correct", dossier.getId(), gestionEditionProjet.getDossier(dossier.getId(), projet.getId()).getId());
    }

    @Test
    public void testGetAllMainFichiers() {
        Fichier fichier = new Fichier("fichierTest.txt");
        FichierDao fichierDao = new FichierDao(em);
        fichierDao.addFichier(fichier);
        gestionEditionProjet.addFichier(fichier, 1, projet.getId(), user.getId(), true);

        Fichier fichier2 = new Fichier("fichierTest2.txt");
        fichier2.setMain(true);
        fichierDao.addFichier(fichier2);
        gestionEditionProjet.addFichier(fichier2, 1, projet.getId(), user.getId(), true);

        List<String> l = gestionEditionProjet.getAllMainFichiers(projet.getId(), user.getId());
        assertEquals("Le nombre de fichiers main est incorrect", 1, l.size());
        assertEquals("Le fichier main est incorrect", fichier2.getName(), l.get(0));
    }

    @Test
    public void testGetFichiersWithJavaDoc() {

    }
}
