package dao;

import model.Page;
import model.Projet;
import model.Wiki;

import java.util.List;

/**
 * Created by pierremarsot on 12/10/2016.
 */
public interface IWikiDao
{
    List<Page> getPages(int idProjet, int idUser);
    Wiki add(Projet projet);
    Page getHomePage(int idProjet, int idUser);
    Wiki get(int idProjet, int idUser);
}
