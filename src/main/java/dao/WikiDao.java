package dao;


import model.Page;
import model.Projet;
import model.Wiki;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class WikiDao extends BaseDao implements IWikiDao
{

    public WikiDao(EntityManager em)
    {
        super(em);
    }

    /**
     * Permet de récupérer toutes les pages du wiki d'un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des pages du wiki du projet
     */
    @Override
    public List<Page> getPages(int idProjet, int idUser)
    {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT p FROM Page p JOIN p.wiki w JOIN w.projet pro JOIN pro.users u WHERE" +
                    " pro.id = :idProjet AND u.id = :idUser", Page.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("idUser", idUser)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet d'ajouter un wiki à un projet
     * @param projet - le projet cible
     * @return - le wiki créé
     */
    @Override
    public Wiki add(Projet projet)
    {
        try
        {
            if (projet == null) {
                return null;
            }

            Wiki wiki = new Wiki();
            wiki.setProjet(projet);

            projet.setWiki(wiki);

            em.persist(wiki);

            return wiki;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer la page d'accueil du wiki
     * @param idProjet - l'id du projet
     * @param idUser - id de l'user connecté
     * @return - la page d'accueil
     */
    @Override
    public Page getHomePage(int idProjet, int idUser)
    {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return null;
            }

            return em.createQuery("SELECT p FROM Page p INNER JOIN p.wiki w INNER JOIN w.projet pro INNER JOIN pro.users u WHERE" +
                    " p.home = :home AND pro.id = :idProjet AND u.id = :idUser", Page.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("home", true)
                    .setParameter("idUser", idUser)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer le wiki du projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - le wiki du projet
     */
    @Override
    public Wiki get(int idProjet, int idUser)
    {
        try
        {
            if (idUser < 1 || idProjet < 1) {
                return null;
            }

            return em.createQuery("SELECT w FROM Wiki w INNER JOIN w.projet p INNER JOIN p.users u" +
                    " WHERE p.id = :idProjet AND u.id = :id", Wiki.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("id", idUser)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
