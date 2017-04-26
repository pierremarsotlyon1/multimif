package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionProjet;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

public class ProjetDaoTest extends TestCase {
    private ProjetDao projetDao;
    private EntityManager em;
    private User user;
    private Projet projet;

    public ProjetDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        projetDao= new ProjetDao(em);
        projetDao.beginTransaction();

        UserDao userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        projetDao.rollback();
        em.close();
    }

    @Test
    public void testAdd() {
        assertTrue("Le projet n'a pas été ajouté",projetDao.add(projet, user));
    }

    @Test
    public void testFindById() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertNotNull("Le projet n'a pas ete trouve",projetDao.findById(projet.getId(), user.getId()));
    }

    @Test
    public void testFindAll() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertNotNull("Le projet n'a pas ete trouve",projetDao.findAll(user.getId()));
    }

    @Test
    public void testRemove() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertTrue("Le projet n'a pas été supprimé",projetDao.remove(projet));
    }

    @Test
    public void existProjet() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertTrue("Le projet n'existe pas",projetDao.existProjet(projet.getName(), user.getId()));
    }

    @Test
    public void testGetAllUsers() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertNotNull("Aucun user n'a pu être trouvé pour ce projetID",projetDao.getAllUsers(projet.getId()));
    }

    @Test
    public void testGetAllUsersWithoutCurrentUser() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertNotNull("Impossible de trouver des Users",projetDao.getAllUsersWithoutCurrentUser(projet.getId(), user.getId()));
    }

    @Test
    public void testFindByIdProjet() {
        projetDao.add(projet, user);
        projet.addUser(user);
        user.addProjet(projet);
        assertNotNull("Impossible de trouver un projet",projetDao.findById(projet.getId(), user.getId()));
    }
}
