package dao;

import model.JavaDocFichier;

import javax.persistence.EntityManager;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class JavaDocFichierDao extends BaseDao implements IJavaDocFichierDao
{
    public JavaDocFichierDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer l'entité de base de la javadoc d'un fichier
     * @param idFichier - id du fichier
     * @return - la JavadocFichier
     */
    @Override
    public JavaDocFichier getJavaDocFichier(int idFichier)
    {
        try
        {
            if (idFichier < 1) {
                return null;
            }

            return em.createQuery("SELECT j FROM JavaDocFichier j WHERE j.fichier.id = :idFichier", JavaDocFichier.class)
                    .setParameter("idFichier", idFichier)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
