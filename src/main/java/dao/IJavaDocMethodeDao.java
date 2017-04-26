package dao;

import model.JavaDocMethode;

import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public interface IJavaDocMethodeDao
{
    List<JavaDocMethode> getJavaDocMethodes(int idJavaDocFichier);
}
