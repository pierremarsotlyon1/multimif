package dao;

import model.JavaDocMethode;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class JavaDocMethodeDao extends BaseDao implements IJavaDocMethodeDao
{
    public JavaDocMethodeDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer la liste des méthodes d'un fichier
     * @param idJavaDocFichier - id du fichier
     * @return - la liste des méthodes
     */
    @Override
    public List<JavaDocMethode> getJavaDocMethodes(int idJavaDocFichier) {
        try
        {
            if (idJavaDocFichier < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT f FROM JavaDocMethode f WHERE f.javaDocFichier.id = :idJavaDocFichier", JavaDocMethode.class)
                    .setParameter("idJavaDocFichier", idJavaDocFichier)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }
}
