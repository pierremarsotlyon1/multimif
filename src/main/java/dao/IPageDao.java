package dao;

import model.Page;
import model.Wiki;

/**
 * Created by pierremarsot on 12/10/2016.
 */
public interface IPageDao
{
    Page findById(int idPage);
    Page get(int idProjet, int idUser, int idPage);
    Page add(Page page, Wiki wiki);
    boolean update(Page page);
    boolean remove(int idPage);
}
