package metier;

import controller.GestionController;
import dao.DroitDao;
import dao.UserDao;
import junit.framework.TestCase;
import model.Droit;
import model.Projet;
import model.ProjetDroit;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionProjetTest extends TestCase {

    private GestionProjet gestionProjet;
    private EntityManager em;
    private User user;
    private UserDao userDao;
    private List<Projet> lProjets;

    public GestionProjetTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionProjet.beginTransaction();

        userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        lProjets = new ArrayList<>();
        Projet p1 = new Projet();
        p1.setName("projetTest1");
        lProjets.add(p1);
        Projet p2 = new Projet();
        p2.setName("projetTest2");
        lProjets.add(p2);
        int i = 0;
        for(Projet p : lProjets) {
            lProjets.set(i,gestionProjet.addProjet(p, user.getId()));
            i++;
        }
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();

        GestionGit gestionGit = new GestionGit();
        for(Projet p : lProjets) {
            gestionGit.remove(p);
        }

        gestionProjet.rollback();
        em.close();
    }

    @Test
    public void testGetAllProjetUser() {
        List<Projet> projets = gestionProjet.getAllProjetUser(user.getId());
        assertNotNull("Les projets de l'utilisateur ne sont pas retournes", projets);
        assertEquals("Le nombre de projets est incorrect", 2, projets.size());
        assertEquals("Un des projets est incorrect", lProjets.get(0).getId(), projets.get(0).getId());
        assertEquals("Un des projets est incorrect", lProjets.get(1).getId(), projets.get(1).getId());
    }

    @Test
    public void testAddProjet() {
        for(Projet p : lProjets) {
            assertNotNull("Le projet n'a pas ete ajoute", p);
        }
    }

    @Test
    public void testGetProjet() {
        for(Projet p : lProjets) {
            assertEquals("Le projet retourne est incorrect", p.getId(), gestionProjet.getProjet(p.getId(), user.getId()).getId());
        }
    }

    @Test
    public void testAddUserProjet() {
        User collabo = new User("test2@mail.fr","password2");
        collabo = userDao.add(collabo);
        assertTrue("Le collaborateur n'a pas ete ajoute au projet", gestionProjet.addUserProjet(lProjets.get(0).getId(), user.getId(), collabo.getId()));
    }

    @Test
    public void testDeleteUserProjet() {
        User collabo = new User("test2@mail.fr","password2");
        collabo = userDao.add(collabo);
        gestionProjet.addUserProjet(lProjets.get(0).getId(), user.getId(), collabo.getId());
        assertTrue("Le collaborateur n'a pas ete supprime", gestionProjet.deleteUserProjet(lProjets.get(0).getId(), collabo.getId()));
    }

    @Test
    public void testGetAllUsersProjet() {
        User collabo = new User("test2@mail.fr","password2");
        collabo = userDao.add(collabo);
        gestionProjet.addUserProjet(lProjets.get(0).getId(), user.getId(), collabo.getId());

        List<User> users = gestionProjet.getAllUsersProjet(lProjets.get(0).getId());
        assertNotNull("Les utilisateurs du projet n'ont pas ete trouves", users);
        assertEquals("Le nombre d'utilisateurs est incorrect", 2, users.size());
        assertEquals("Un des utilisateurs est incorrect", user.getId(), users.get(0).getId());
        assertEquals("Un des utilisateurs est incorrect", collabo.getId(), users.get(1).getId());
    }

    @Test
    public void testGetAllUsersProjetWithoutCurrentUser() {
        User collabo = new User("test2@mail.fr","password2");
        collabo = userDao.add(collabo);
        gestionProjet.addUserProjet(lProjets.get(0).getId(), user.getId(), collabo.getId());

        List<User> users = gestionProjet.getAllUsersProjetWithoutCurrentUser(lProjets.get(0).getId(), user.getId());
        assertNotNull("Les utilisateurs du projet n'ont pas ete trouves", users);
        assertEquals("Le nombre d'utilisateurs est incorrect", 1, users.size());
        assertEquals("Un des utilisateurs est incorrect", collabo.getId(), users.get(0).getId());
    }

    @Test
    public void testDeleteProjet() {
        assertFalse("Le projet n'aurait pas du etre supprime (utilisateur non administrateur)", gestionProjet.deleteProjet(lProjets.get(0).getId(), user.getId(),false));
    }
}
