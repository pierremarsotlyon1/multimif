package dao;

import model.Branche;

import javax.persistence.EntityManager;
import java.util.List;

public class BrancheDao extends BaseDao implements IBrancheDao
{
    public BrancheDao(EntityManager em) {
        super(em);
    }

    @Override
    public Branche add(Branche branche, int idProjet, int idUser) {
        return null;
    }

    @Override
    public boolean delete(int idBranche, int idProjet, int idUser) {
        return false;
    }

    @Override
    public Branche get(int idBranche, int idProjet, int idUser) {
        return null;
    }

    @Override
    public List<Branche> getAll(int idProjet, int idUser) {
        return null;
    }

    @Override
    public boolean merge(Branche mere, Branche fille, int idProjet, int idUser) {
        return false;
    }
}
