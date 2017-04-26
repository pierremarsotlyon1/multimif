package dao;

import model.Projet;
import model.User;

import java.util.List;

/**
 * Created by pierremarsot on 11/10/2016.
 */
public interface IProjetDao
{
    boolean add(Projet object, User user);
    Projet findById(int idProjet, int idUser);
    List<Projet> findAll(int idUser);
    boolean existProjet(String name, int idUser);
    List<User> getAllUsers(int idProjet);
    List<User> getAllUsersWithoutCurrentUser(int idProjet, int idUser);

    Projet findByIdProjet(int idProjet);
}
