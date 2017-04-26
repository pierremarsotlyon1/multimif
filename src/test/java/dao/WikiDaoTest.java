package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import metier.GestionWiki;
import model.Page;
import model.Projet;
import model.User;
import model.Wiki;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class WikiDaoTest extends TestCase {

    private WikiDao wikiDao;
    private UserDao userDao;
    private GestionProjet gestionProjet;
    private EntityManager em;
    private User user;
    private Projet projet;
    private Wiki wiki;
    private ArrayList<Page> listePages;

    public WikiDaoTest() {
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

        wikiDao = new WikiDao(em);
        wiki = wikiDao.add(projet);

        GestionWiki gestionWiki = new GestionWiki(em);
        Page pHome = new Page();
        pHome.setTitle("page home");
        Page p1 = new Page();
        p1.setTitle("page 1");
        Page p2 = new Page();
        p2.setTitle("page 2");
        gestionWiki.addPage(projet.getId(),user.getId(),pHome);
        gestionWiki.addPage(projet.getId(),user.getId(),p1);
        gestionWiki.addPage(projet.getId(),user.getId(),p2);

        listePages = new ArrayList<>();
        listePages.add(pHome);
        listePages.add(p1);
        listePages.add(p2);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();

        PageDao pageDao = new PageDao(em);

        gestionProjet.deleteProjet(projet.getId(),user.getId(),false);
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);

        userDao.rollback();

        wikiDao = null;
        userDao = null;
        gestionProjet = null;
        user = null;
        wiki = null;

        em.close();
    }

    @Test
    public void testGetPages() {
        int i = 0;
        for(Page page : wikiDao.getPages(projet.getId(),user.getId())) {
            assertEquals("Les pages du wiki sont incorrectes", listePages.get(i).getId(), page.getId());
            i++;
        }
    }

    @Test
    public void testAdd() {
        assertNotNull(wiki);
    }

    @Test
    public void testGetHomePage() {
        assertEquals("La page home n'est pas correcte", listePages.get(0), wikiDao.getHomePage(projet.getId(), user.getId()));
    }

    @Test
    public void testGet() {
        assertEquals("Le wiki n'est pas retourne", wiki, wikiDao.get(projet.getId(), user.getId()));
    }
}
