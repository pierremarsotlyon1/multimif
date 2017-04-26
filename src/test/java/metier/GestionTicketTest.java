package metier;

import controller.GestionController;
import dao.UserDao;
import junit.framework.TestCase;
import model.Projet;
import model.Ticket;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionTicketTest extends TestCase {

    private GestionTicket gestionTicket;
    private EntityManager em;
    private User user;
    private Projet projet;
    private List<Ticket> lTickets;

    public GestionTicketTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionTicket = new GestionTicket(em);
        gestionTicket.beginTransaction();

        UserDao userDao = new UserDao(em);
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        GestionProjet gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());

        lTickets = new ArrayList<>();
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        lTickets.add(t1);
        lTickets.add(t2);

        int i = 0;
        for(Ticket t : lTickets) {
            t.setIdTracker("1");
            t.setIdPrioriteTicket("1");
            t.setIdStatutTicket("1");
            t.setIdUserAssigne("" + user.getId());
            t.setSujet("sujetTest");
            i++;
        }
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);
        gestionTicket.rollback();
        em.close();
    }

    @Test
    public void testGetAllTickets() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        lTickets.set(1, gestionTicket.addTicket(lTickets.get(1), projet.getId(), user.getId()));
        int i = 0;
        for(Ticket t : gestionTicket.getAllTickets(projet.getId())) {
            assertEquals("Les tickets ne sont pas corrects", lTickets.get(i).getId(), t.getId());
            i++;
        }
    }

    @Test
    public void testGetAlltracker() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        lTickets.set(1, gestionTicket.addTicket(lTickets.get(1), projet.getId(), user.getId()));
        assertNotNull("Les trackers des tickets ne sont pas retournes", gestionTicket.getAlltracker());
    }

    @Test
    public void testGetAllPrioriteTickets() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        lTickets.set(1, gestionTicket.addTicket(lTickets.get(1), projet.getId(), user.getId()));
        assertNotNull("Les priorites des tickets ne sont pas retournees", gestionTicket.getAllPrioriteTickets());
    }

    @Test
    public void testGetAllStatutTickets() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        lTickets.set(1, gestionTicket.addTicket(lTickets.get(1), projet.getId(), user.getId()));
        assertNotNull("Les statuts des tickets ne sont pas retournes", gestionTicket.getAllStatutTickets());
    }

    @Test
    public void testAddTicket() {
        assertNotNull("Le ticket n'a pas ete ajoute", gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
    }

    @Test
    public void testUpdateTicket() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        lTickets.get(0).setDescription("teeeesst");
        assertNotNull("Le ticket n'a pas ete mis a jour", gestionTicket.updateTicket(lTickets.get(0),projet.getId(), user.getId(), lTickets.get(0).getId()));
    }

    @Test
    public void testGetTicket() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        assertEquals(lTickets.get(0).getId(), gestionTicket.getTicket(projet.getId(),lTickets.get(0).getId()).getId());
    }

    @Test
    public void testDeleteTicket() {
        lTickets.set(0, gestionTicket.addTicket(lTickets.get(0), projet.getId(), user.getId()));
        assertNotNull("Le ticket n'a pas ete supprime", gestionTicket.deleteTicket(projet.getId(), lTickets.get(0).getId()));
    }
}
