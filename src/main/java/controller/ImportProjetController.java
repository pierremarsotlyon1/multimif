package controller;

import metier.GestionImport;
import model.Projet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pierremarsot on 15/11/2016.
 */
@Controller
public class ImportProjetController extends GestionController
{

    @RequestMapping(value = "/projet/import", method = RequestMethod.GET)
    public String importProjet(Model model)
    {
        model.addAttribute("projet", new Projet());
        return "import/index";
    }

    @RequestMapping(value = "/projet/import", method = RequestMethod.POST)
    public String importProjet(@RequestParam("file") MultipartFile file, @ModelAttribute("projet") Projet projet,
                               HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        GestionImport gestionImport = new GestionImport(em);

        try
        {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;

            int idUser = getIdUser();

            gestionImport.beginTransaction();

            projet = gestionImport.importProjet(projet, file, idUser);
            if(projet != null && gestionImport.commit())
            {
                addMessage(GestionController.SUCCESS_MESSAGE, "Le projet a bien été importé");
            }
            else
            {
                gestionImport.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de l'import du projet");
            }

            return "redirect:/projets";
        }
        finally
        {
            gestionImport.close();
        }
    }
}
