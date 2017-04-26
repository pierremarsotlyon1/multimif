package dao;

import model.Dossier;

/**
 * Created by pierremarsot on 19/10/2016.
 */
public interface IDossierDao
{
    Dossier get(int idDossier, int idProjet);
}
