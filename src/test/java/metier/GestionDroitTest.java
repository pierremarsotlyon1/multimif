package metier;

import controller.GestionController;
import dao.DroitDao;
import junit.framework.TestCase;
import model.Droit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionDroitTest extends TestCase {

    private GestionDroit gestionDroit;
    private EntityManager em;
    private List<Droit> lDroits;

    public GestionDroitTest() {
        super();
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
        em = GestionController.createEntityManager();
        gestionDroit = new GestionDroit(em);
        gestionDroit.beginTransaction();

        DroitDao droitDao = new DroitDao(em);
        Droit d1 = droitDao.add("testDroit1");
        Droit d2 = droitDao.add("testDroit2");

        lDroits = new ArrayList<>();
        lDroits.add(d1);
        lDroits.add(d2);
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
        gestionDroit.rollback();
        em.close();
    }

    @Test
    public void testAdd() {
        for(Droit d : lDroits) {
            assertNotNull("Le droit n'a pas ete ajoute", d);
        }
    }

    @Test
    public void testGet() {
        int i = lDroits.get(0).getId();
        for(Droit d : lDroits) {
            System.out.println(d.getId());
            assertEquals("Le droit n'est pas correct", d.getId(), gestionDroit.get(i).getId());
            i++;
        }
        assertEquals("Le droit n'est pas correct", lDroits.get(0).getId(), gestionDroit.get(lDroits.get(0).getName()).getId());
        assertEquals("Le droit n'est pas correct", lDroits.get(1).getId(), gestionDroit.get(lDroits.get(1).getName()).getId());
    }

    @Test
    public void testGetAll() {
        List<Droit> droits = gestionDroit.getAll();
        assertEquals("Le nombre de droits est incorrect", 10, droits.size());
    }
}
