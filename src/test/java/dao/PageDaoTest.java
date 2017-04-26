package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import model.Page;
import model.Projet;
import model.User;
import model.Wiki;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

public class PageDaoTest extends TestCase {

    private PageDao pageDao;
    private EntityManager em;
    private Page page;
    private Wiki wiki;
    private Projet projet;
    private User user;
    private UserDao userDao;
    private GestionProjet gestionProjet;

    public PageDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();

        userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());

        pageDao = new PageDao(em);
        WikiDao wikiDao = new WikiDao(em);
        page = new Page();
        page.setTitle("pageTest");
        wiki = new Wiki();
        wiki = wikiDao.add(projet);
        page = pageDao.add(page, wiki);

    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);

        userDao.rollback();

        wiki = null;
        pageDao = null;
        page = null;
        em.close();
    }

    @Test
    public void testFindById() {
        assertEquals("La page n'a pas ete trouvee", page.getId(), pageDao.findById(page.getId()).getId());
    }

    @Test
    public void testGet() {
        assertEquals("La page n'a pas ete retournee", page.getId(), pageDao.get(projet.getId(),user.getId(),page.getId()).getId());
    }

    @Test
    public void testAdd() {
        assertNotNull("Echec de l'ajout de page", page);
    }

    @Test
    public void testUpdate() {
        page.setDescription("teeeeeeest");
        assertTrue(pageDao.update(page));
    }

    @Test
    public void testRemove() {
        assertTrue(pageDao.remove(page.getId()));
    }
}
