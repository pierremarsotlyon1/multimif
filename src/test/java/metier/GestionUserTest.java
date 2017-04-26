package metier;


import controller.GestionController;
import dao.UserDao;
import junit.framework.TestCase;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class GestionUserTest extends TestCase{

    private GestionUser gestionUser;
    private UserDao userDao;
    private User user;
    private EntityManager em;
    private Projet projet;

    public GestionUserTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception{
        super.setUp();
        em = GestionController.createEntityManager();
        gestionUser = new GestionUser(em);
        userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr", "password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        GestionProjet gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());
        user.addProjet(projet);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        userDao.rollback();
        gestionUser = null;
        userDao = null;
        em.close();
    }

    @Test
    public void testCreateGitRepository() {

    }

    @Test
    public void testSearchUser() {

    }

    @Test
    public void testGetEmailUser() {
        int idUser = user.getId();
        assertEquals("L'email utilisateur est incorrect", "test@mail.fr", gestionUser.getEmailUser(idUser));
    }

}
