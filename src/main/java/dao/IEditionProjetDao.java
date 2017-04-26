package dao;

import model.Dossier;
import model.Fichier;

/**
 * Created by pierremarsot on 14/10/2016.
 */
public interface IEditionProjetDao
{
    Fichier addFichier(Fichier fichier);
    Dossier addDossier(Dossier dossier);
    Fichier updateFichier(Fichier fichier, int idFichier, int idProjet, int idUser);
    Dossier updateDossier(Dossier dossier, int idDossier, int idProjet, int idUser);
    boolean deleteElement(int idElement, int idProjet, int idUser);
    Fichier getFichier(int idFichier, int idProjet, int idUser);
    Dossier getDossier(int idDossier, int idProjet, int idUser);
}
