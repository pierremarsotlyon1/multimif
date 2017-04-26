package dao;

import model.JavaDocImport;

import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public interface IJavaDocImportDao
{
    List<JavaDocImport> getJavaDocImport(int idJavaDocFichier);
}
