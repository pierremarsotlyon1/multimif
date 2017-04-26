package dao;

import model.Projet;
import model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class ProjetDao extends BaseDao implements IProjetDao
{
    public ProjetDao(EntityManager em)
    {
        super(em);
    }

    /**
     * Permet d'ajouter un projet
     * @param projet - le projet à ajouter
     * @param user - l'user qui ajoute le projet
     * @return - succés de l'ajout du projet
     */
    @Override
    public boolean add(Projet projet, User user)
    {
        try
        {
            if (projet == null) {
                return false;
            }

            //On regarde si l'user n'a pas déjà un projet du même nom
            boolean exist = em.createQuery("SELECT COUNT(p.id) FROM Projet p JOIN p.users u WHERE u.id = :idUser " +
                    "AND p.name = :nomProjet", Long.class)
                    .setParameter("idUser", user.getId())
                    .setParameter("nomProjet", projet.getName())
                    .getFirstResult() > 0;
            if (exist) {
                return false;
            }

            //On persist le projet
            em.persist(projet);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de récupérer un projet via son id
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - le projet
     */
    @Override
    public Projet findById(int idProjet, int idUser)
    {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return null;
            }

            return em.createQuery("SELECT p FROM Projet p JOIN p.users u WHERE p.id = :idProjet AND u.id = :idUser", Projet.class)
                    .setParameter("idProjet", idProjet)
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
     * Permet de récuperer tous les projets d'un user
     * @param idUser - id de l'user
     * @return - liste des projets de l'user
     */
    @Override
    public List<Projet> findAll(int idUser)
    {
        try
        {
            if (idUser < 1) {
                return new ArrayList<Projet>();
            }

            return em.createQuery("SELECT p FROM Projet p INNER JOIN p.users u WHERE u.id = :idUser", Projet.class)
                    .setParameter("idUser", idUser)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<Projet>();
        }
    }

    /**
     * Permet de savoir si un user possède un projet qui porte le nom en paramètre
     * @param name - le nom du projet à vérifier
     * @param idUser - id de l'user
     * @return - existance du projet
     */
    @Override
    public boolean existProjet(String name, int idUser)
    {
        try
        {
            if (name == null || name.isEmpty() || idUser < 1) {
                return true;
            }

            int count = em.createQuery("SELECT COUNT(p.id) FROM Projet p WHERE p.name = :name")
                    .setParameter("name", name)
                    .getFirstResult();
            return count > 0;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return true;
        }
    }

    /**
     * Permet de récupérer tous les users d'un projet
     * @param idProjet - id du projet
     * @return - la liste des users d'un projet
     */
    @Override
    public List<User> getAllUsers(int idProjet)
    {
        try
        {
            if (idProjet < 1) {
                return new ArrayList<User>();
            }

            return em.createQuery("SELECT u FROM User u JOIN u.projets p WHERE p.id = :idProjet", User.class)
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
     * Permet de récupérer la liste des users d'un projet sans l'user connecté
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des users d'un projet sans l'user connecté
     */
    public List<User> getAllUsersWithoutCurrentUser(int idProjet, int idUser)
    {
        try
        {
            if (idProjet < 1) {
                return new ArrayList<User>();
            }

            return em.createQuery("SELECT u FROM User u JOIN u.projets p WHERE p.id = :idProjet AND u.id != :idUser", User.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("idUser", idUser)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<User>();
        }
    }

    /**
     * Permet de trouver un projet via son id
     * @param idProjet - id du projet
     * @return - le projet
     */
    @Override
    public Projet findByIdProjet(int idProjet) {
        try
        {
            if (idProjet < 1) {
                return null;
            }

            return em.createQuery("SELECT p FROM Projet p WHERE p.id = :idProjet", Projet.class)
                    .setParameter("idProjet", idProjet)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    public boolean remove(Projet projet)
    {
        try
        {
            if (projet == null) {
                return false;
            }

            em.remove(projet);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
