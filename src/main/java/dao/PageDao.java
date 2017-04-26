package dao;

import model.Page;
import model.Wiki;

import javax.persistence.EntityManager;

public class PageDao extends BaseDao implements IPageDao
{

    public PageDao(EntityManager em)
    {
        super(em);
    }

    /**
     * Permet de récupérer une page d'un wiki via son id
     * @param idPage - id de la page
     * @return - la page du wiki
     */
    @Override
    public Page findById(int idPage)
    {
        try
        {
            if (idPage < 1) {
                return null;
            }

            return em.find(Page.class, idPage);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer la page d'un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param idPage - id de la page
     * @return - la page du wiki
     */
    @Override
    public Page get(int idProjet, int idUser, int idPage)
    {
        try
        {
            if (idUser < 1 || idProjet < 1 || idPage < 1) {
                return null;
            }

            return em.createQuery("SELECT p FROM Page p INNER JOIN p.wiki w INNER JOIN w.projet pro INNER JOIN " +
                    " pro.users u WHERE u.id = :id AND pro.id = :idProjet AND p.id = :idPage", Page.class)
                    .setParameter("id", idUser)
                    .setParameter("idProjet", idProjet)
                    .setParameter("idPage", idPage)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet d'ajouter une page à un wiki
     * @param page - la page à ajouter
     * @param wiki - le wiki cible
     * @return - la page ajoutée
     */
    @Override
    public Page add(Page page, Wiki wiki)
    {
        try
        {
            if(page == null || wiki == null)
                return null;

            //On fait les liaisons
            page.setWiki(wiki);
            wiki.getPages().add(page);

            em.persist(page);

            return page;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de modifier la page d'un wiki
     * @param page - la page avec les nouvelles données
     * @return - succès de la modification
     */
    @Override
    public boolean update(Page page)
    {
        try
        {
            if (page == null) {
                return false;
            }

            Page old = findById(page.getId());
            if (old == null) {
                return false;
            }

            old.setDescription(page.getDescription());
            old.setTitle(page.getTitle());

            em.persist(old);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de supprimer une page d'un wiki
     * @param idPage - id de la page
     * @return - succès de la suppression de la page
     */
    @Override
    public boolean remove(int idPage)
    {
        try
        {
            if (idPage < 1) {
                return false;
            }

            Page page = findById(idPage);
            if (page == null) {
                return false;
            }

            em.remove(page);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
