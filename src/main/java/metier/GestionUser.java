package metier;

import dao.UserDao;
import model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionUser extends GestionBase {

    private UserDao userDao;

    public GestionUser(EntityManager em) {
        super(em);
        userDao = new UserDao(em);
    }

    /**
     * Permet de connecter un User
     * @param user - l'user à connecter
     * @return - User connecté
     */
    public User connexion(User user)
    {
        try
        {
            if(user == null) return null;

            return userDao.get(user.getEmail(), user.getPassword());
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de créer un compte à un User
     * @param user - l'user auquel on doit créer un compte
     * @return - l'user avec le compte créé
     */
    public User register(User user)
    {
        try
        {
            if(user == null) return null;

            //On regarde si les mots de passe sont identiques
            if(!user.samePassword()) return null;

            //On regarde si un utilisateur existe avec l'email
            if(userDao.userExist(user.getEmail())) return null;

            //On ajoute l'utilisateur
            user = userDao.add(user);

            return user;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de valider la confirmation par email
     * @param idUser - id de l'user
     * @return - user avce compte confirmé
     */
    public User confirmation(int idUser)
    {
        try
        {
            if(idUser < 1) return null;

            User user = userDao.findById(idUser);
            if(user == null) return null;

            user.setActivate(true);

            return user;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de chercher un user
     * @param email - email avec lequel chercher
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - liste des users qui contiennent l'email
     */
    public List<User> searchUser(String email, int idProjet, int idUser) {
        try
        {
            return userDao.searchUser(email, idProjet, idUser);
        }
        catch(Exception e)
        {
            return new ArrayList<User>();
        }
    }

    /**
     * Permet de récupérer l'email d'un user
     * @param idUser - id de l'user
     * @return - email de l'user
     */
    public String getEmailUser(int idUser)
    {
        try
        {
            return userDao.getEmailUser(idUser);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
