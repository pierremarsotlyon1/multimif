package controller;

import metier.GestionWiki;
import model.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by pierremarsot on 12/10/2016.
 */
@Controller
public class WikiController extends GestionController
{
    private GestionWiki gestionWiki;

    @RequestMapping(value = "/projet/{idProjet}/wiki", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") String idProjet,
                        Model model, HttpServletRequest request)
    {
        indexWiki(Integer.parseInt(idProjet), 0, model, request);

        return "wiki/index";
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/{idPage}", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") String idProjet, @PathVariable(value = "idPage") String idPage,
                        Model model, HttpServletRequest request)
    {
        indexWiki(Integer.parseInt(idProjet), Integer.parseInt(idPage), model, request);

        return "wiki/index";
    }

    /**
     * Permet de récupérer la page home ou demandée ainsi que la liste des pages du wiki
     * @param idProjet
     * @param idPage
     * @param model
     * @param request
     * @return
     */
    private boolean indexWiki(int idProjet, int idPage, Model model, HttpServletRequest request)
    {
        EntityManager em = createEntityManager();
        gestionWiki = new GestionWiki(em);

        try
        {
            this.request = request;
            int idUser = getIdUser();

            Page homePage;

            //On regarde si l'utilisateur demande la page d'accueil du wiki (namePage empty)
            if(idPage < 1)
            {
                //On récup la page home
                homePage = gestionWiki.getHomePage(idProjet, idUser);

                //Si on a une page home (donc au moins une page) on récup la liste des pages
                if(homePage != null)
                    getPages(idProjet, idUser, model);

                model.addAttribute("page", homePage);
            }
            //Sinon on récup la page demandée via son nom
            else
            {
                Page page = gestionWiki.getPage(idProjet, idUser, idPage);
                model.addAttribute("page", page);

                //On récup la liste des pages
                getPages(idProjet, idUser, model);
            }

            return true;
        }
        finally
        {
            gestionWiki.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/edit/{idPage}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idPage") int idPage,
                       Model model, HttpServletRequest request)
    {
        EntityManager em = createEntityManager();
        gestionWiki = new GestionWiki(em);

        try
        {
            this.model = model;
            this.request = request;

            int idUser = getIdUser();

            //On récup la page et on l'affecte au model
            model.addAttribute("page", gestionWiki.getPage(idProjet, idUser, idPage));

            return "wiki/edit";
        }
        finally
        {
            gestionWiki.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/edit/{idPage}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("page") Page page, @PathVariable(value = "idProjet") int idProjet,
                       @PathVariable(value="idPage") int idPage, Model model, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionWiki = new GestionWiki(em);

        try
        {
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            if(page != null)
            {
                gestionWiki.beginTransaction();

                //On demande l'update de la page
                page = gestionWiki.updatePage(page, idPage);
                if(page != null && gestionWiki.commit())
                {
                    //On affiche un message de success
                    addMessage(GestionController.SUCCESS_MESSAGE, "La page a bien été modifiée");
                    return "redirect:/projet/"+idProjet+"/wiki/"+idPage;
                }

                gestionWiki.rollback();
                //On affiche un message d'erreur
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la modification de la page");
            }

            return "wiki/edit";
        }
        finally
        {
            gestionWiki.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/new", method = RequestMethod.GET)
    public String addPage(@PathVariable(value = "idProjet") String idProjet, Model model)
    {
        model.addAttribute("page", new Page());
        return "wiki/new";
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/new", method = RequestMethod.POST)
    public String addPage(@ModelAttribute("page") Page page, @PathVariable(value = "idProjet") String strIdProjet, Model model,
                          HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionWiki = new GestionWiki(em);

        try
        {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;
            int idUser = getIdUser();
            int idProjet = Integer.parseInt(strIdProjet);

            if(page != null)
            {
                gestionWiki.beginTransaction();

                page = gestionWiki.addPage(idProjet, idUser, page);
                if(page != null && gestionWiki.commit())
                {
                    addMessage(GestionController.SUCCESS_MESSAGE, "La page a bien été ajoutée");
                    return "redirect:/projet/"+idProjet+"/wiki/"+page.getId();
                }

                gestionWiki.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la création de la page");
            }

            return "wiki/new";
        }
        finally
        {
            gestionWiki.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/wiki/delete/{idPage}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idPage") int idPage,
                         Model model, HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionWiki = new GestionWiki(em);

        try
        {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;

            gestionWiki.beginTransaction();

            boolean delete = gestionWiki.deletePage(idPage, idProjet, getIdUser());
            if(delete && gestionWiki.commit())
            {
                addMessage(GestionController.SUCCESS_MESSAGE, "La page a bien été supprimée");
            }
            else
            {
                gestionWiki.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la suppression de la page");
            }

            return "redirect:/projet/"+idProjet+"/wiki";
        }
        finally
        {
            gestionWiki.close();
        }
    }

    /**
     * Permet de récupérer les pages du wiki
     * @param model
     * @return
     */
    private boolean getPages(int idProjet, int idUser, Model model)
    {
        try
        {
            if (model == null) {
                return false;
            }

            List<Page> pages = gestionWiki.getAllPages(idProjet, idUser);
            model.addAttribute("pages", pages);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
