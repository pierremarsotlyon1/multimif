package metier;

import dao.*;
import model.JavaDocFichier;

import javax.persistence.EntityManager;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class GestionJavaDoc extends GestionBase
{
    private JavaDocFichierDao javaDocFichierDao;
    private JavaDocImportDao javaDocImportDao;
    private JavaDocClassesDao javaDocClassesDao;
    private JavaDocMethodeDao javaDocMethodeDao;
    private JavaDocAttributDao javaDocAttributDao;

    public GestionJavaDoc(EntityManager em) {
        super(em);
        javaDocFichierDao = new JavaDocFichierDao(em);
        javaDocImportDao = new JavaDocImportDao(em);
        javaDocClassesDao = new JavaDocClassesDao(em);
        javaDocMethodeDao = new JavaDocMethodeDao(em);
        javaDocAttributDao = new JavaDocAttributDao(em);
    }

    /**
     * Permet de récupérer un objet JavaDocFichier liè à un fichier
     * @param idFichier - id du fichier
     * @return - l'objet JavaDocFichier
     */
    public JavaDocFichier getJavaDocFichierComplet(int idFichier)
    {
        try
        {
            if(idFichier < 1) return null;

            //On récup le début de la javadoc
            return javaDocFichierDao.getJavaDocFichier(idFichier);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
