package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoTest extends TestCase{

    private UserDao userDao;
    private EntityManager em;
    private User user;
    private Projet projet;
    private GestionProjet gestionProjet;

    public UserDaoTest() { super(); }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr", "password");
        user.setActivate(true);
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());
    }

    @AfterClass
    protected void tearDown() throws  Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);

        userDao.rollback();
        userDao = null;
        user = null;
        em.close();
    }

    @Test
    public void testAdd() {
        assertNotNull("L'utilisateur n'a pas ete cree",user);
    }

    @Test
    public void testFindById() {
        User userTest = userDao.findById(user.getId());
        assertNotNull("L'utilisateur n'a pas ete trouve",userTest);
    }

    @Test
    public void testRemove() {
        boolean rm = userDao.remove(user);
        assertTrue("L'utilisateur n'a pas ete supprime", rm);
    }

    @Test
    public void testGet() {
        User userTest = userDao.get(user.getEmail(),"password");
        assertEquals("L'utilisateur n'a pas ete trouve", user.getId(), userTest.getId());
    }

    @Test
    public void testUserExist() {
    }

    @Test
    public void testGetImageProfil() {
        user.setImage("imageTest");
        String img = userDao.getImageProfil(user.getId());
        assertEquals("Pas de chemin vers l'image trouve", "imageTest", img);
    }

    @Test
    public void testHaveProjet() {
        boolean haveProjet = userDao.haveProjet(user.getId(), projet.getId());
        assertTrue("Pas de projet trouve", haveProjet);
    }

    @Test
    public void testSearchUser() {
    }

    @Test
    public void testGetEmailUser() {
        String email = userDao.getEmailUser(user.getId());
        assertEquals("L'email utilisateur n'a pas ete trouve", user.getEmail(), email);
    }
}
