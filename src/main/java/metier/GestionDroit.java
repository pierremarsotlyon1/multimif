package metier;

import dao.DroitDao;
import model.Droit;

import javax.persistence.EntityManager;
import java.util.List;

public class GestionDroit extends GestionBase {
    private DroitDao droitDao;

    public GestionDroit(EntityManager em) {
        super(em);
        droitDao = new DroitDao(em);
    }

    public Droit add(String name) {
        return droitDao.add(name);
    }

    public Droit get(int id) {
        return droitDao.get(id);
    }

    public Droit get(String name) {
        return droitDao.get(name);
    }

    public List<Droit> getAll() {
        return droitDao.getAll();
    }
}
