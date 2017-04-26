package controller;

import metier.GestionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController extends GestionController
{
    @RequestMapping(value = "/projet/{idProjet}/chat", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") int idProjet, Model model, HttpServletRequest request) {
        this.request = request;
        this.model = model;

        EntityManager em = createEntityManager();
        GestionUser gestionUser = new GestionUser(em);

        //On récup l'email de l'user
        String emailUser = gestionUser.getEmailUser(getIdUser());

        //On l'ajout au model
        model.addAttribute("emailUser", emailUser);

        return "chat/index";
    }
}
