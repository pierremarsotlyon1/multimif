package metier;

import controller.GestionController;
import dao.UserDao;
import dao.WikiDao;
import junit.framework.TestCase;
import model.Page;
import model.Projet;
import model.User;
import model.Wiki;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

public class GestionWikiTest extends TestCase {

    private GestionWiki gestionWiki;
    private EntityManager em;
    private Page page;
    private Projet projet;
    private User user;

    public GestionWikiTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionWiki = new GestionWiki(em);
        gestionWiki.beginTransaction();

        UserDao userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        GestionProjet gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());

        WikiDao wikiDao = new WikiDao(em);
        Wiki wiki = wikiDao.add(projet);

        page = new Page();
        page.setTitle("pageTest");
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);

        gestionWiki.rollback();
        em.close();
    }

    @Test
    public void testGetHomePage() {
        gestionWiki.addPage(projet.getId(),user.getId(),page);
        assertNotNull(gestionWiki.getHomePage(projet.getId(),user.getId()));
    }

    @Test
    public void testGetPage() {
        gestionWiki.addPage(projet.getId(),user.getId(),page);
        assertEquals("La page retourneee n'est pas correcte",page.getId(),gestionWiki.getPage(projet.getId(), user.getId(), page.getId()).getId());
    }

    @Test
    public void testGetAllPages() {
        gestionWiki.addPage(projet.getId(),user.getId(),page);
        assertEquals("La page retourneee n'est pas correcte",page.getId(),gestionWiki.getAllPages(projet.getId(), user.getId()).get(0).getId());
    }

    @Test
    public void testUpdatePage() {
        gestionWiki.addPage(projet.getId(),user.getId(),page);
        assertEquals("La page n'a pas ete mise a jour", page.getId(), gestionWiki.updatePage(page,page.getId()).getId());
    }

    @Test
    public void testAddPage() {
        assertNotNull("La page n'a pas ete ajoutee", gestionWiki.addPage(projet.getId(),user.getId(),page));
    }

    @Test
    public void testDeletePage() {
        gestionWiki.addPage(projet.getId(),user.getId(),page);
        assertTrue(gestionWiki.deletePage(page.getId(),projet.getId(),user.getId()));
    }
}
