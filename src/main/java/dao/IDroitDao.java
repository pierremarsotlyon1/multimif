package dao;

import model.Droit;

import java.util.List;

/**
 * Created by pierremarsot on 21/10/2016.
 */
public interface IDroitDao {
    Droit add(String name);
    Droit get(int id);
    Droit get(String name);
    List<Droit> getAll();
    List<Droit> getDroitsUser(int idUser, int idProjet);
    boolean exist(String name);
    boolean exist(int id);
}
