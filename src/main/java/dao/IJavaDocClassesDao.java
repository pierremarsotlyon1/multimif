package dao;

import model.JavaDocClasse;

import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public interface IJavaDocClassesDao
{
    List<JavaDocClasse> getJavaDocClasses(int idJavaDocFichier);
}
