package dao;

import model.JavaDocAttribut;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class JavaDocAttributDao extends BaseDao implements IJavaDocAttributDao
{
    public JavaDocAttributDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer les attributs d'une méthode de la javadoc
     * @param idJavaDocMethode - id de la méthode
     * @return - la liste des attributs de la méthode
     */
    @Override
    public List<JavaDocAttribut> getJavaDocAttributs(int idJavaDocMethode) {
        try
        {
            if (idJavaDocMethode < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT a FROM JavaDocAttribut a WHERE a.javaDocMethode.id = :idJavaDocMethode", JavaDocAttribut.class)
                    .setParameter("idJavaDocMethode", idJavaDocMethode)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }
}
