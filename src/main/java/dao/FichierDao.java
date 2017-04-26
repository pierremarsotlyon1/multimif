package dao;

import model.Fichier;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 16/11/2016.
 */
public class FichierDao extends BaseDao implements IFichierDao {
    public FichierDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer un fichier via son id
     * @param idFichier - id du fichier
     * @return - le fichier
     */
    @Override
    public Fichier get(int idFichier) {
        try
        {
            if (idFichier < 1) {
                return null;
            }

            return em.createQuery("SELECT f FROM Fichier f WHERE f.id = :idFichier", Fichier.class)
                    .setParameter("idFichier", idFichier)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet d'ajouter un fichier en bdd
     * @param fichier - le fichier à ajouter
     * @return - le fichier ajouté
     */
    @Override
    public Fichier addFichier(Fichier fichier)
    {
        try
        {
            if (fichier == null) {
                return null;
            }


            em.persist(fichier);

            return fichier;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les fichiers executables
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des fichiers executables
     */
    @Override
    public List<Fichier> getAllMainFichiers(int idProjet, int idUser)
    {
        try{
            if (idProjet < 1 || idUser < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT f FROM Fichier f INNER JOIN f.projet pro INNER JOIN " +
                    " pro.users u WHERE u.id = :idUser AND pro.id = :idProjet AND f.main = :isMain", Fichier.class)
                    .setParameter("idUser", idUser)
                    .setParameter("idProjet",idProjet)
                    .setParameter("isMain",true)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste des fichiers qui ont un javadoc (après notre parsing)
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des fichiers qui possèdent une javadoc
     */
    @Override
    public List<Fichier> getFichiersWithJavaDoc(int idProjet, int idUser) {
        try
        {
            if (idProjet < 1 || idUser < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT f FROM Fichier f LEFT JOIN FETCH f.javaDocFichier jd " +
                    " INNER JOIN f.projet pro INNER JOIN " +
                    " pro.users u WHERE u.id = :idUser AND pro.id = :idProjet " +
                    "AND (SELECT jd FROM JavaDocFichier jd WHERE jd.fichier.id = f.id) != null", Fichier.class)
                    .setParameter("idUser", idUser)
                    .setParameter("idProjet",idProjet)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de supprimer un fichier du projet
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès du remove
     */
    public boolean deleteFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            //On récup le fichier
            Fichier fichier = get(idFichier);
            if (fichier == null || fichier.getProjet() == null || fichier.getProjet().getId() != idProjet) {
                return false;
            }

            em.remove(fichier);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de mettre un fichier non executable
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès
     */
    public boolean setUnMainFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            //On récup le fichier
            Fichier fichier = get(idFichier);
            if (fichier == null || fichier.getProjet() == null || fichier.getProjet().getId() != idProjet) {
                return false;
            }

            fichier.setMain(false);
            em.persist(fichier);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de mettre un fichier executable
     * @param idProjet - id du projet
     * @param idFichier - id du fichier
     * @return - succès
     */
    public boolean setMainFichier(int idProjet, int idFichier)
    {
        try
        {
            if (idProjet < 1 || idFichier < 1) {
                return false;
            }

            //On récup le fichier
            Fichier fichier = get(idFichier);
            if (fichier == null || fichier.getProjet() == null || fichier.getProjet().getId() != idProjet) {
                return false;
            }

            fichier.setMain(true);
            em.persist(fichier);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
