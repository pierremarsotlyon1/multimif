package dao;

import model.Branche;

import java.util.List;

/**
 * Created by pierremarsot on 14/10/2016.
 */
public interface IBrancheDao
{
    Branche add(Branche branche, int idProjet, int idUser);
    boolean delete(int idBranche, int idProjet, int idUser);
    Branche get(int idBranche, int idProjet, int idUser);
    List<Branche> getAll(int idProjet, int idUser);
    boolean merge(Branche mere, Branche fille, int idProjet, int idUser);
}
