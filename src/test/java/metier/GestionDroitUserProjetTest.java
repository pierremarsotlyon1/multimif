package metier;

import controller.GestionController;
import dao.UserDao;
import junit.framework.TestCase;
import model.Projet;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import viewModel.ViewModelDroitUserProjet;

import javax.persistence.EntityManager;

public class GestionDroitUserProjetTest extends TestCase {

    private GestionDroitUserProjet gestionDroitUserProjet;
    private EntityManager em;
    private User user;
    private Projet projet;

    public GestionDroitUserProjetTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionDroitUserProjet = new GestionDroitUserProjet(em);
        gestionDroitUserProjet.beginTransaction();

        UserDao userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        GestionProjet gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        gestionDroitUserProjet.rollback();
        em.close();
    }

    @Test
    public void testManageDroitUser() {
        ViewModelDroitUserProjet droits = new ViewModelDroitUserProjet();
        assertTrue("Les droits n'ont pas ete modifies", gestionDroitUserProjet.manageDroitUser(projet.getId(), user.getId(), droits));
    }

    @Test
    public void testGetDroitsUser() {
        assertEquals("Les droits de l'utilisateur sont inscorrects", gestionDroitUserProjet.getAllDroits(), gestionDroitUserProjet.getDroitsUser(user.getId(),projet.getId()));
    }

    @Test
    public void testGetAllDroits() {
        assertEquals("Le nombre de droits n'est pas correct", 8,gestionDroitUserProjet.getAllDroits().size());
    }
}
