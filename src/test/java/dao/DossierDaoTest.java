package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import model.Dossier;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

public class DossierDaoTest extends TestCase {

    private DossierDao dossierDao;
    private User user;
    private Projet projet;
    private EntityManager em;
    private Dossier dossier;

    public DossierDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        dossierDao = new DossierDao(em);
        dossierDao.beginTransaction();

        UserDao userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        GestionProjet gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());

        dossier = new Dossier("dossierTest");
        dossier = dossierDao.addDossier(dossier);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        dossierDao.rollback();
        em.close();
    }

    @Test
    public void testGet() {

    }

    @Test
    public void testAddDossier() {
        assertNotNull("Le dossier n'a pas ete ajoute", dossier);
    }
}
