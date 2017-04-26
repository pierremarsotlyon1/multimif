package dao;

import model.JavaDocClasse;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class JavaDocClassesDao extends BaseDao implements IJavaDocClassesDao
{
    public JavaDocClassesDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer les classes (extends/implémentes) d'une classe via la javadoc
     * @param idJavaDocFichier - id du fichier
     * @return - la liste des classes
     */
    @Override
    public List<JavaDocClasse> getJavaDocClasses(int idJavaDocFichier) {
        try
        {
            if (idJavaDocFichier < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT j FROM JavaDocClasse j WHERE j.javaDocFichier.id = :idJavaDocFichier", JavaDocClasse.class)
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
