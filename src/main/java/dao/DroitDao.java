package dao;

import model.Droit;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DroitDao extends BaseDao implements IDroitDao {
    public DroitDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet d'ajouter un droit en bdd
     * @param name - le nom du droit
     * @return - le droit ajouté
     */
    @Override
    public Droit add(String name) {
        try
        {
            if (name == null || name.isEmpty()) {
                return null;
            }

            //On regarde si le droit exist
            if (exist(name)) {
                return null;
            }

            Droit droit = new Droit();
            droit.setName(name);

            em.persist(droit);

            return droit;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de supprimer un droit
     * @param droit - le droit à supprimer
     * @return - succès du droit supprimé
     */
    public boolean delete(Droit droit)
    {
        try
        {
            if (droit == null) {
                return false;
            }

            em.remove(droit);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de récupérer un droit via son id
     * @param id - id du droit
     * @return - le droit
     */
    @Override
    public Droit get(int id) {
        try
        {
            if (id < 1) {
                return null;
            }

            return em.find(Droit.class, id);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer un droit via son nom
     * @param name - le nom du droit
     * @return - le droit
     */
    @Override
    public Droit get(String name) {
        try
        {
            if (name == null || name.isEmpty()) {
                return null;
            }

            return em.createQuery("SELECT d FROM Droit d WHERE d.name = :name", Droit.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les droits en bdd
     * @return - la liste des droits
     */
    @Override
    public List<Droit> getAll() {
        try
        {
            return em.createQuery("SELECT d FROM Droit d", Droit.class)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste des droits d'un user d'un projet
     * @param idUser - id de l'user
     * @param idProjet - id du projet
     * @return - la liste des droits de l'user d'un projet
     */
    @Override
    public List<Droit> getDroitsUser(int idUser, int idProjet) {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return new ArrayList<Droit>();
            }

            return em.createQuery("SELECT d FROM Droit d INNER JOIN d.projetDroits pd INNER JOIN pd.user u INNER JOIN" +
                    " pd.projet p WHERE u.id = :idUser AND p.id = :idProjet", Droit.class)
                    .setParameter("idUser", idUser)
                    .setParameter("idProjet", idProjet)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de savoir si un droit existe via un nom
     * @param name - le nom à vérifier
     * @return - existance du droit
     */
    @Override
    public boolean exist(String name) {
        try
        {
            if (name == null || name.isEmpty()) {
                return false;
            }

            return get(name) != null;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de vérifier qu'un droit existe via son id
     * @param id - id du droit
     * @return - existance du droit
     */
    @Override
    public boolean exist(int id) {
        try
        {
            return get(id) != null;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
