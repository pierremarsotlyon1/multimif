package controller;

import metier.GestionDroitUserProjet;
import metier.GestionProjet;
import model.Droit;
import model.Projet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import viewModel.CreateElementProjet;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pierremarsot on 11/10/2016.
 */
@Controller
public class ProjectController extends GestionController {
    private GestionProjet gestionProjet;
    private GestionDroitUserProjet gestionDroitUserProjet;


    @RequestMapping(value = "/projets", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try {
            this.request = request;
            this.model = model;

            //On récup l'id de l'user en session
            int idUser = getIdUser();

            //On récup la liste de ses projets
            List<Projet> p = gestionProjet.getAllProjetUser(idUser);
            Map<Projet, List<Droit>> projetDroits = new HashMap<>();
            for (Projet proj : p) {
                projetDroits.put(proj, gestionDroitUserProjet.getDroitsUser(idUser, proj.getId()));
            }

            //On insert dans le model de la vue
            model.addAttribute("projets", projetDroits);
            model.addAttribute("imageProfil", getImageUser());

            return "projet/index";
        } finally {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/add", method = RequestMethod.GET)
    public String addGet(Model model) {
        model.addAttribute("projet", new Projet());
        return "projet/add";
    }

    @RequestMapping(value = "/projet/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("projet") Projet projet, HttpServletRequest request, Model model,
                          RedirectAttributes redirectAttributes) {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;

            if (projet != null) {
                //On commence la transaction
                gestionProjet.beginTransaction();

                //Création du projet
                projet = gestionProjet.addProjet(projet, getIdUser());
                if (projet != null && gestionProjet.commit()) {
                    addMessage(GestionController.SUCCESS_MESSAGE, "Votre projet a bien été créé");
                    return "redirect:/projets";
                }

                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de l'ajout du projet");
            }

            return "projet/add";
        } finally {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}", method = RequestMethod.GET)
    public String resume(@PathVariable(value = "idProjet") int idProjet, Model model, HttpServletRequest request) {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try {
            this.request = request;
            int idUser = getIdUser();

            //On récup le projet voulu
            Projet projet = gestionProjet.getProjet(idProjet, idUser);
            if (projet == null)
                return "redirect:/projets";
            HttpSession session = request.getSession();

            //On ajoute les droits de l'user dans la session
            session.setAttribute(DROIT_SESSION, gestionDroitUserProjet.getDroitsUser(idUser, idProjet));
            if (!((List<Droit>) session.getAttribute(DROIT_SESSION)).contains(new Droit(Droit.Lecture))) {
                return "redirect:/projet/{idProjet}/wiki";
            }
            //On l'ajout au model
            model.addAttribute("projet", projet);
            model.addAttribute("createElementProjet", new CreateElementProjet());
            return "projet/resume";
        } finally {
            gestionProjet.close();
        }
    }


    @RequestMapping(value = "/projet/delete/{idProjet}/{admin}", method = RequestMethod.GET)
    public String remove(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "admin") boolean admin, HttpServletRequest request, Model model,
                         RedirectAttributes redirectAttributes) {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try {
            this.model = model;
            this.request = request;
            this.redirectAttributes = redirectAttributes;


            gestionProjet.beginTransaction();

            boolean success = gestionProjet.deleteProjet(idProjet, getIdUser(), admin);
            if (success) {
                gestionProjet.commit();
                addMessage(GestionController.SUCCESS_MESSAGE, "Le projet a bien été supprimé");
            } else {
                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la suppression du projet");
            }

            return "redirect:/projets";
        } finally {
            gestionProjet.close();
        }
    }
}
