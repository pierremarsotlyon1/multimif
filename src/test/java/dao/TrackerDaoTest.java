package dao;

import controller.GestionController;
import junit.framework.TestCase;
import model.Tracker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TrackerDaoTest extends TestCase {
    private TrackerDao trackerDao;
    private EntityManager em;

    public TrackerDaoTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        trackerDao = new TrackerDao(em);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        em.close();
        trackerDao = null;
    }

    @Test
    public void testFindById() {
        /* bdd pré-remplie avec ces 3 trackers : Bug, Build, Feature */
        assertEquals("Le tracker est incorrect","Bug",trackerDao.findById(1).getNom());
        assertEquals("Le tracker est incorrect","Build",trackerDao.findById(2).getNom());
        assertEquals("Le tracker est incorrect","Feature",trackerDao.findById(3).getNom());
    }

    @Test
    public void testGetAll() {
        List<Tracker> l = new ArrayList<>();
        Tracker t1 = new Tracker();
        t1.setId(1);
        t1.setNom("Bug");
        Tracker t2 = new Tracker();
        t2.setId(2);
        t2.setNom("Build");
        Tracker t3 = new Tracker();
        t3.setId(3);
        t3.setNom("Feature");
        l.add(t1);
        l.add(t2);
        l.add(t3);
        int i = 0;
        for(Tracker t : trackerDao.getAll()) {
            assertEquals(l.get(i).getId(),t.getId());
            assertEquals(l.get(i).getNom(),t.getNom());
            i++;
        }
    }
}
