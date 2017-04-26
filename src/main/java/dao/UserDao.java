package dao;

import metier.Md5;
import model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class UserDao extends BaseDao implements IUserDao
{
    public UserDao(EntityManager em)
    {
        super(em);
    }

    /**
     * Permet d'ajouter un User
     * @param user
     * @return
     */
    @Override
    public User add(User user)
    {
        try
        {
            if (user == null) {
                return null;
            }

            user.setPassword(Md5.encode(user.getPassword()));
            em.persist(user);

            return user;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de trouver un User via son id
     * @param id
     * @return
     */
    @Override
    public User findById(int id)
    {
        try
        {
            return id < 1 ? null : em.find(User.class, id);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(User user)
    {
        try
        {
            if (user == null) {
                return false;
            }

            em.remove(user);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de récupérer un utilisateur via son email et mot de passe
     * @param email
     * @param password
     * @return
     */
    @Override
    public User get(String email, String password)
    {
        try
        {
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                return null;
            }

            return em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.activate = true", User.class)
                    .setParameter("email", email)
                    .setParameter("password", Md5.encode(password))
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de savoir si un utilisateur existe avec le même email
     * @param email
     * @return
     */
    @Override
    public boolean userExist(String email)
    {
        try
        {
            if (email == null || email.isEmpty()) {
                return true;
            }

            return em.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getFirstResult() > 0;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return true;
        }
    }

    /**
     * Permet de récupérer l'image de profil d'un user
     * @param idUser - id de l'user
     * @return - l'image de profil
     */
    @Override
    public String getImageProfil(int idUser)
    {
        try
        {
            if (idUser < 1) {
                return null;
            }

            return em.createQuery("SELECT u.image FROM User u WHERE u.id = :idUser", String.class)
                    .setParameter("idUser", idUser)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de savoir si un user possède un projet
     * @param idUser - id de l'user
     * @param idProjet - id du projet
     * @return - l'user a oui ou non le projet
     */
    @Override
    public boolean haveProjet(int idUser, int idProjet)
    {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return false;
            }

            return em.createQuery("SELECT COUNT(p.id) FROM Projet p JOIN p.users u WHERE u.id = :idUser " +
                    " AND p.id = :idProjet AND u.activate = true", Long.class)
                    .setParameter("idUser", idUser)
                    .setParameter("idProjet", idProjet)
                    .getSingleResult() > 0;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de chercher un user via un email qui n'est pas déjà dans les users du projet cible
     * @param email - email à rechercher
     * @param idProjet - id du projet cible
     * @param idUser - id de l'user connecté
     * @return
     */
    @Override
    public List<User> searchUser(String email, int idProjet, int idUser) {
        try
        {
            if (email == null || email.isEmpty()) {
                return new ArrayList<User>();
            }

            return em.createQuery("SELECT u FROM User u WHERE u.email LIKE :email AND u.id != :idUser AND u.id NOT IN( " +
                    "(SELECT u.id FROM User u INNER JOIN u.projets p WHERE p.id = :idProjet AND u.activate = true))", User.class)
                    .setParameter("email", "%"+email+"%")
                    .setParameter("idUser", idUser)
                    .setParameter("idProjet", idProjet)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<User>();
        }
    }

    /**
     * Permet de récupérer l'email d'un user
     * @param idUser - id de l'user
     * @return - email de l'user
     */
    @Override
    public String getEmailUser(int idUser)
    {
        try
        {
            if (idUser < 1) {
                return null;
            }

            return em.createQuery("SELECT u.email FROM User u WHERE u.id = :idUser", String.class)
                    .setParameter("idUser", idUser)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
