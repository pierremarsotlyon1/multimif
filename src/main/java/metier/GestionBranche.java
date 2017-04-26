package metier;

import dao.BrancheDao;
import model.Branche;

import javax.persistence.EntityManager;
import java.util.List;

public class GestionBranche extends GestionBase
{
    private BrancheDao brancheDao;
    private GestionGit gestionGit;

    public GestionBranche(EntityManager em) {
        super(em);
        brancheDao = new BrancheDao(em);
        gestionGit = new GestionGit();
    }

    public Branche add(Branche branche, int idProjet, int idUser) {
        return null;
    }

    public boolean delete(int idBranche, int idProjet, int idUser) {
        return false;
    }

    public Branche get(int idBranche, int idProjet, int idUser) {
        return null;
    }

    public List<Branche> getAll(int idProjet, int idUser) {
        return null;
    }

    public boolean merge(Branche mere, Branche fille, int idProjet, int idUser) {
        return false;
    }
}
