package dao;

import controller.GestionController;
import junit.framework.TestCase;
import metier.GestionGit;
import metier.GestionProjet;
import metier.GestionTicket;
import model.Projet;
import model.Ticket;
import model.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoTest extends TestCase {

    private TicketDao ticketDao;
    private EntityManager em;
    private UserDao userDao;
    private User user;
    private GestionProjet gestionProjet;
    private Projet projet;
    private List<Ticket> lTickets;

    public TicketDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        ticketDao = new TicketDao(em);

        userDao = new UserDao(em);
        userDao.beginTransaction();
        user = new User("test@mail.fr","password");
        user = userDao.add(user);

        projet = new Projet();
        projet.setName("projetTest");
        gestionProjet = new GestionProjet(em);
        projet = gestionProjet.addProjet(projet, user.getId());

        lTickets = new ArrayList<>();
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        lTickets.add(t1);
        lTickets.add(t2);

        GestionTicket gestionTicket = new GestionTicket(em);
        int i = 0;
        for(Ticket t : lTickets) {
            t.setIdTracker("1");
            t.setIdPrioriteTicket("1");
            t.setIdStatutTicket("1");
            t.setIdUserAssigne(""+user.getId());
            t.setSujet("sujetTest");
            lTickets.set(i,gestionTicket.addTicket(t, projet.getId(), user.getId()));
            i++;
        }

    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        GestionGit gestionGit = new GestionGit();
        gestionGit.remove(projet);

        userDao.rollback();

        user = null;
        projet = null;
        lTickets.clear();
        lTickets = null;
        gestionProjet = null;
        userDao = null;
        ticketDao = null;
        em.close();
    }

    @Test
    public void testFindById() {
        for(Ticket t : lTickets)
            assertNotNull("Le ticket n'est pas trouve", ticketDao.findById(t.getId()));
    }

    @Test
    public void testGetAll() {
        int i = 0;
        for(Ticket t : ticketDao.getAll(projet.getId())) {
            assertEquals("Le ticket n'est pas correct",lTickets.get(i).getId(),t.getId());
            i++;
        }
    }

    @Test
    public void testGetTicket() {
        int i = 0;
        for(Ticket t : lTickets) {
            assertEquals("Le ticket n'est pas correct",t.getId(),ticketDao.getTicket(projet.getId(),t.getId()).getId());
            i++;
        }
    }
}
