package dao;

import model.Fichier;

import java.util.List;

/**
 * Created by pierremarsot on 16/11/2016.
 */
public interface IFichierDao
{
    Fichier get(int idFichier);
    Fichier addFichier(Fichier fichier);
    List<Fichier> getAllMainFichiers(int idProjet, int idUser);
    List<Fichier> getFichiersWithJavaDoc(int idProjet, int idUser);
}
