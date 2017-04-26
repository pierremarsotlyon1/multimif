package controller;

import metier.GestionDroitUserProjet;
import metier.GestionProjet;
import metier.GestionUser;
import model.Droit;
import model.User;
import model.UserJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import viewModel.ViewModelDroitUserProjet;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
@Controller
public class CollaborateurController extends GestionController {
    private GestionProjet gestionProjet;
    private GestionDroitUserProjet gestionDroitUserProjet;

    @RequestMapping(value = "/projet/{idProjet}/collaborateur", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") int idProjet, Model model, HttpServletRequest request) {

        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);

        try
        {
            this.request = request;
            this.model = model;

            //On récup les users du projet
            List<User> users = gestionProjet.getAllUsersProjet(idProjet);

            //On l'ajout au model
            model.addAttribute("users", users);

            return "collaborateur/index";
        }
        finally
        {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/collaborateur/searchCollaborateur", method = RequestMethod.POST)
    public
    @ResponseBody
    ArrayList<UserJson> searchCollaborateur(@RequestParam String emailUser, @PathVariable(value = "idProjet") int idProjet,
                                            HttpServletRequest request) {
        EntityManager em = createEntityManager();
        GestionUser gestionUser = new GestionUser(em);

        try
        {
            this.request = request;

            List<User> list = gestionUser.searchUser(emailUser, idProjet, getIdUser());
            ArrayList<UserJson> response = new ArrayList<UserJson>();
            for (User u : list) {
                UserJson userJson = new UserJson(u.getId(), u.getEmail());
                response.add(userJson);
            }

            return response;
        }
        finally
        {
            gestionUser.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/collaborateur/add/{idCollaborateur}", method = RequestMethod.GET)
    public String add(@PathVariable(value = "idProjet") int idProjet,
                      @PathVariable(value = "idCollaborateur") int idCollaborateur, Model model
            , HttpServletRequest request, RedirectAttributes redirectAttributes)
    {

        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);

        try
        {
            this.request = request;
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            gestionProjet.beginTransaction();

            boolean success = gestionProjet.addUserProjet(idProjet, getIdUser(), idCollaborateur);
            if(success && gestionProjet.commit())
            {
                addMessage(GestionController.SUCCESS_MESSAGE, "Le collaborateur a bien été ajouté");
            }
            else
            {
                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de l'ajout du collaborateur");
            }

            return "redirect:/projet/"+idProjet+"/collaborateur";
        }
        finally
        {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/collaborateur/remove/{idCollaborateur}", method = RequestMethod.GET)
    public String Delete(@PathVariable(value = "idProjet") int idProjet,
                      @PathVariable(value = "idCollaborateur") int idCollaborateur, Model model
            , HttpServletRequest request) {

        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);

        try
        {
            this.request = request;
            this.model = model;

            gestionProjet.beginTransaction();

            boolean success = gestionProjet.deleteUserProjet(idProjet, idCollaborateur);
            if(success && gestionProjet.commit())
            {
                addMessage(GestionController.SUCCESS_MESSAGE, "Le collaborateur a bien été supprimé");
            }
            else
            {
                gestionProjet.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la suppression du collaborateur");
            }

            return "redirect:/projet/"+idProjet+"/collaborateur";
        }
        finally
        {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/collaborateur/{idUser}/manageDroits", method = RequestMethod.GET)
    public String manageDroit(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idUser") int idUser,
                              Model model, HttpServletRequest request) {
        EntityManager em = createEntityManager();
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try
        {
            this.request = request;
            this.model = model;

            //On récup la liste des droits
            List<Droit> droits = gestionDroitUserProjet.getAllDroits();

            //On récup la liste des droits actuels de l'utilisateur cible
            List<Droit> droitsUser = gestionDroitUserProjet.getDroitsUser(idUser, idProjet);

            //Création du viewModel
            ViewModelDroitUserProjet viewModelDroitUserProjet = new ViewModelDroitUserProjet();
            for(Droit d : droits)
            {
                boolean find = false;
                for(Droit d2 : droitsUser)
                {
                    if (d2 != d) {
                        continue;
                    }
                    find = true;
                    break;
                }

                d.setChecked(find);
                viewModelDroitUserProjet.add(d);
            }

            //On ajoute le viewModel dans le model
            model.addAttribute("viewModelDroit", viewModelDroitUserProjet);

            return "collaborateur/manageDroits";
        }
        finally
        {
            gestionDroitUserProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/collaborateur/{idUser}/manageDroits", method = RequestMethod.POST)
    public String manageDroit(@ModelAttribute(value = "viewModelDroit") ViewModelDroitUserProjet viewModelDroitUserProjets,
                              @PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idUser") int idUser,
                              Model model, HttpServletRequest request) {
        EntityManager em = createEntityManager();
        gestionDroitUserProjet = new GestionDroitUserProjet(em);

        try
        {
            this.request = request;
            this.model = model;

            if(viewModelDroitUserProjets != null)
            {
                gestionDroitUserProjet.beginTransaction();

                boolean success = gestionDroitUserProjet.manageDroitUser(idProjet, idUser, viewModelDroitUserProjets);
                if(success && gestionDroitUserProjet.commit())
                {
                    addMessage(GestionController.SUCCESS_MESSAGE, "Les droits ont bien été modifiés");
                }
                else
                {
                    gestionDroitUserProjet.rollback();
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la modification des droits");
                }
            }

            return "redirect:/projet/"+idProjet+"/collaborateur";
        }
        finally
        {
            gestionDroitUserProjet.close();
        }
    }

}
