package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import model.Droit;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;

public class DroitDaoTest extends TestCase {

    private DroitDao droitDao;
    private EntityManager em;
    private User user;
    private UserDao userDao;
    private Projet projet;
    private GestionProjet gestionProjet;

    public DroitDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        droitDao=new DroitDao(em);
        droitDao.beginTransaction();

        userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        droitDao.rollback();
        em.close();
    }

    @Test
    public void testAdd() {
        assertNotNull("Le nouveau droit n'a pas été créé",droitDao.add("Nouveau Droit"));
    }

    @Test
    public void testGet() {
        assertNotNull("Le droit administrateur n'a pas pu etre récupéré",droitDao.get("administrateur"));
    }

    @Test
    public void testGetAll() {
        ArrayList<Droit> droits= (ArrayList<Droit>) droitDao.getAll();
        assertNotNull("Aucun droit n'a été récupéré",droits);
    }

    @Test
    public void testGetDroitsUser() {
        assertNotNull("Les droits pour ces UserId et ProjetID n'ont pu etre obtenus",droitDao.getDroitsUser(user.getId(),projet.getId()));
    }

    @Test
    public void testExist() {
        assertTrue("Ce droit est sensé exister",droitDao.exist(1));
        assertTrue("Ce droit est sensé exister",droitDao.exist(2));
        assertTrue("Ce droit est sensé exister",droitDao.exist(3));
        assertTrue("Ce droit est sensé exister",droitDao.exist(4));
        assertTrue("Ce droit est sensé exister",droitDao.exist(5));
        assertTrue("Ce droit est sensé exister",droitDao.exist(6));
        assertFalse("Ce droit n'est pas sensé exister",droitDao.exist(10));

    }
}
