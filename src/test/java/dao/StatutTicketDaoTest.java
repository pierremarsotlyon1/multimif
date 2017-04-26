package dao;

import controller.GestionController;
import junit.framework.TestCase;
import model.StatutTicket;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class StatutTicketDaoTest extends TestCase {

    private StatutTicketDao statutTicketDao;
    private EntityManager em;

    public StatutTicketDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        statutTicketDao = new StatutTicketDao(em);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        statutTicketDao = null;
        em.close();
    }

    @Test
    public void testFindById() {
        /* bdd pré-remplie avec ces 3 statuts de tickets : New, In progress, Resolved */
        assertEquals("Le statut du ticket est incorrect","New",statutTicketDao.findById(1).getName());
        assertEquals("Le statut du ticket est incorrect","In progress",statutTicketDao.findById(2).getName());
        assertEquals("Le statut du ticket est incorrect","Resolved",statutTicketDao.findById(3).getName());
    }

    @Test
    public void testGetAll() {
        List<StatutTicket> l = new ArrayList<>();
        StatutTicket t1 = new StatutTicket();
        t1.setId(1);
        t1.setName("New");
        StatutTicket t2 = new StatutTicket();
        t2.setId(2);
        t2.setName("In progress");
        StatutTicket t3 = new StatutTicket();
        t3.setId(3);
        t3.setName("Resolved");
        l.add(t1);
        l.add(t2);
        l.add(t3);
        int i = 0;
        for(StatutTicket t : statutTicketDao.getAll()) {
            assertEquals(l.get(i).getId(),t.getId());
            assertEquals(l.get(i).getName(),t.getName());
            i++;
        }
    }
}
