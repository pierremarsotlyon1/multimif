package dao;

import controller.GestionController;
import junit.framework.TestCase;
import model.PrioriteTicket;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PrioriteTicketDaoTest extends TestCase {

    private PrioriteTicketDao prioriteTicketDao;
    private EntityManager em;

    public PrioriteTicketDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        prioriteTicketDao = new PrioriteTicketDao(em);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        prioriteTicketDao = null;
        em.close();
    }

    @Test
    public void testFindById() {
        /* bdd pré-remplie avec ces 2 priorités de tickets : Urgent, Normal */
        assertEquals("La priorite du ticket est incorrecte","Urgent",prioriteTicketDao.findById(1).getName());
        assertEquals("La priorite du ticket est incorrecte","Normal",prioriteTicketDao.findById(2).getName());
    }

    @Test
    public void testGetAll() {
        List<PrioriteTicket> l = new ArrayList<>();
        PrioriteTicket t1 = new PrioriteTicket();
        t1.setId(1);
        t1.setName("Urgent");
        PrioriteTicket t2 = new PrioriteTicket();
        t2.setId(2);
        t2.setName("Normal");
        l.add(t1);
        l.add(t2);
        int i = 0;
        for(PrioriteTicket t : prioriteTicketDao.getAll()) {
            assertEquals(l.get(i).getId(),t.getId());
            assertEquals(l.get(i).getName(),t.getName());
            i++;
        }
    }
}
