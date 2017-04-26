package dao;

import model.User;

import java.util.List;


public interface IUserDao
{
    User add(User object);
    User findById(int id);
    boolean remove(User object);
    User get(String email, String password);
    boolean userExist(String email);
    String getImageProfil(int idUser);
    boolean haveProjet(int idUser, int idProjet);
    List<User> searchUser(String email, int idProjet, int idUser);
    String getEmailUser(int idUser);
}
