package dao;

import model.JavaDocAttribut;

import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public interface IJavaDocAttributDao
{
    List<JavaDocAttribut> getJavaDocAttributs(int idJavaDocMethode);
}
