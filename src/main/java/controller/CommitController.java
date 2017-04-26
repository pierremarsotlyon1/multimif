package controller;

import metier.GestionEditionProjet;
import metier.GestionGit;
import metier.GestionProjet;
import model.Commit;
import model.Projet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 14/11/2016.
 */
@Controller
public class CommitController extends GestionController
{
    private GestionGit gestionGit;
    private GestionProjet gestionProjet;
    private GestionEditionProjet gestionEditionProjet;

    public CommitController()
    {
        gestionGit = new GestionGit();
    }

    @RequestMapping(value = "/projet/{idProjet}/commits", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") int idProjet, HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);

        try
        {
            this.model = model;
            this.request = request;

            int idUser = getIdUser();

            Projet projet = gestionProjet.getProjet(idProjet, idUser);
            if(projet == null)
            {
                return "redirect:/projet/"+idProjet;
            }

            ArrayList<Commit> commits = gestionGit.getCommits(projet);

            model.addAttribute("commits", commits);
            return "commit/index";
        }
        finally
        {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/commits/checkout/{idCommit}", method = RequestMethod.GET)
    public String checkout(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idCommit") String idCommit,
                           HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);

        try
        {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;

            int idUser = getIdUser();

            Projet projet = gestionProjet.getProjet(idProjet, idUser);
            if(projet == null)
            {
                return "redirect:/projet/"+idProjet;
            }

            gestionProjet.beginTransaction();

            if(!gestionGit.checkout(projet, idCommit))
            {
                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors du checkout");
                return "redirect:/projet/"+idProjet+"/commits";
            }

            gestionEditionProjet = new GestionEditionProjet(em);

            List<String> arborescence = gestionGit.getArborescenceRepo(projet);
            if(!gestionEditionProjet.replaceArborescence(idProjet, arborescence, idUser))
            {
                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur critique !!");
            }

            gestionProjet.commit();

            return "redirect:/projet/"+idProjet+"/commits";
        }
        finally
        {
            gestionProjet.close();
        }
    }
}
