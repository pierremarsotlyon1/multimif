package dao;

import model.JavaDocImport;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class JavaDocImportDao extends BaseDao implements IJavaDocImportDao
{
    public JavaDocImportDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer la liste des imports d'une classe de la javadoc
     * @param idJavaDocFichier - id de la classe JavadocFichier
     * @return - la liste des imports
     */
    @Override
    public List<JavaDocImport> getJavaDocImport(int idJavaDocFichier)
    {
        try
        {
            if (idJavaDocFichier < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT j FROM JavaDocImport j WHERE j.javaDocFichier.id = :idJavaDocFichier", JavaDocImport.class)
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
