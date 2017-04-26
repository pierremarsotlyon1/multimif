package metier;

import dao.PageDao;
import dao.ProjetDao;
import dao.WikiDao;
import model.Page;
import model.Projet;
import model.Wiki;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionWiki extends GestionBase {
    private PageDao pageDao;
    private WikiDao wikiDao;
    private ProjetDao projetDao;

    public GestionWiki(EntityManager em) {
        super(em);
        pageDao = new PageDao(em);
        wikiDao = new WikiDao(em);
        projetDao = new ProjetDao(em);
    }

    /**
     * Permet de récupérer la page d'accueil du wiki
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la page d'accueil du wiki
     */
    public Page getHomePage(int idProjet, int idUser) {
        try {
            return wikiDao.getHomePage(idProjet, idUser);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de récupérer une page du wiki
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param idPage - id de la page voulue
     * @return - la page voulue
     */
    public Page getPage(int idProjet, int idUser, int idPage) {
        try {
            return pageDao.get(idProjet, idUser, idPage);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de récupérer toutes les pages
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des pages du wiki
     */
    public List<Page> getAllPages(int idProjet, int idUser) {
        try {
            return wikiDao.getPages(idProjet, idUser);
        } catch (Exception e) {
            return new ArrayList<Page>();
        }
    }

    /**
     * Permet de modifier une page du wiki
     * @param page - la page avec les nouvelles données
     * @param idPage - id de la page
     * @return - la page modifiée
     */
    public Page updatePage(Page page, int idPage) {
        try {
            if (page == null || idPage < 1) return null;

            //On attribut l'id de la page à l'objet
            page.setId(idPage);

            //On modifie la page
            pageDao.update(page);

            return page;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet d'ajouter une page au wiki
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param page - la page a ajouter
     * @return - la page ajoutée
     */
    public Page addPage(int idProjet, int idUser, Page page) {
        try
        {
            if(idProjet < 1 || idUser < 1 || page == null || page.getTitle() == null || page.getTitle().isEmpty())
                return null;

            //On récup le wiki du projet
            Wiki wiki = wikiDao.get(idProjet, idUser);

            //Si le wiki est null alors on le crée
            if (wiki == null) {
                //On récup le projet courant
                Projet projet = projetDao.findById(idProjet, idUser);

                //Si le projet n'est pas null c'est qu'on peut continuer
                if (projet == null) return null;

                //Création du wiki
                wiki = wikiDao.add(projet);
            }

            //A ce stade, on devrait obligatoirement avoir un wiki
            if (wiki == null) return null;

            //On regarde si on a une page home
            Page homePage = wikiDao.getHomePage(idProjet, idUser);

            //Si on a pas de page home, la page que l'on ajoute sera la page home
            if (homePage == null)
                page.setHome(true);
                //Sinon on marque la page comme "non page home"
            else
                page.setHome(false);

            //On insert la page dans le wiki
            page = pageDao.add(page, wiki);

            //Si la page n'est pas null c'est que l'insert c'est bien passé
            if (page != null) {
                return page;
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de supprimer un page du wiki
     * @param idPage - id de la page
     * @param idProjet - id du projet
     * @param idUser - id de l'user
     * @return - succés si suppression de la page
     */
    public boolean deletePage(int idPage, int idProjet, int idUser)
    {
        try
        {
            if(idPage < 1 || idProjet < 1 || idUser < 1) return false;

            //On récup la page du wiki
            Page page = pageDao.get(idProjet, idUser, idPage);
            if(page == null) return false;

            //On supprime la page
            em.remove(page);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
