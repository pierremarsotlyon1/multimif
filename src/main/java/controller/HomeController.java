package controller;

import metier.GestionEmail;
import metier.GestionUser;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends GestionController {
    private GestionUser gestionUser;

    private enum enumRegister {
        Success,
        ErrorSendEmail,
        Error
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "home/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deconnexion")
    public String deconnexion(HttpServletRequest request) {
        request.getSession().invalidate();
        return "home/index";
    }

    @RequestMapping(value = "/connexion", method = RequestMethod.GET)
    public String connexionGet(Model model) {
        model.addAttribute("user", new User());
        return "home/connexion";
    }

    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public String connexionPost(@ModelAttribute("user") User user, HttpServletRequest request, Model model,
                                RedirectAttributes redirectAttributes) {
        this.request = request;
        this.model = model;
        this.redirectAttributes = redirectAttributes;

        EntityManager em = createEntityManager();
        gestionUser = new GestionUser(em);

        try {
            if (user != null) {
                user = gestionUser.connexion(user);
                if (user != null) {
                    if (setUserSession(user)) {
                        return "redirect:/projets";
                    } else {
                        addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la mise en session de votre compte");
                    }
                } else {
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la récupération de votre compte");
                }
            }

            return "home/connexion";
        } finally {
            em.close();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "home/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request, Model model,
                               RedirectAttributes redirectAttributes) {
        EntityManager em = createEntityManager();
        gestionUser = new GestionUser(em);

        this.request = request;
        this.model = model;
        this.redirectAttributes = redirectAttributes;

        try {
            gestionUser.beginTransaction();

            //On dit que l'on vient d'une création via google
            user.setGoogleAccount(false);

            //On met l'utilisateur comme non actif en attendant la confirmation email
            //user.setActivate(true);
            if(ACTIVATE_CONFIRMATION_EMAIL)
            {
                user.setActivate(false);
            }
            else
            {
                user.setActivate(true);
            }


            //On ajoute l'user en bdd
            enumRegister response = registerUser(user, GestionController.ACTIVATE_CONFIRMATION_EMAIL);
            switch (response) {
                case Success:
                    if(ACTIVATE_CONFIRMATION_EMAIL)
                    {
                        addMessage(GestionController.SUCCESS_MESSAGE, "Votre compte a bien été créé, veuillez cliquer sur le lien dans l'email " +
                                "qui vient de vous être envoyé");
                    }
                    else
                    {
                        if (setUserSession(user)) {
                            return "redirect:/projets";
                        } else {
                            addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la mise en session de votre compte");
                        }
                    }
                    break;
                case Error:
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la création de votre compte");
                    break;
                case ErrorSendEmail:
                    addMessage(GestionController.ERROR_MESSAGE, "Votre compte a bien été créé mais l'email de confirmation n'a pas pu être envoyé," +
                            " veuillez contacter un administrateur");
                    break;
                default:
                    break;
            }

            return "home/register";
        } finally {
            gestionUser.close();
        }
    }

    @RequestMapping(value = "/registerGoogle", method = RequestMethod.POST)
    public String registerGoogle(@ModelAttribute("user") User user, HttpServletRequest request, Model model,
                                 RedirectAttributes redirectAttributes) {
        EntityManager em = createEntityManager();
        gestionUser = new GestionUser(em);

        this.request = request;
        this.model = model;
        this.redirectAttributes = redirectAttributes;

        try {
            gestionUser.beginTransaction();

            //On dit que l'on vient d'une création via google
            user.setGoogleAccount(true);
            user.setActivate(true);

            //On ajoute l'user en bdd
            enumRegister response = registerUser(user, GestionController.ACTIVATE_CONFIRMATION_EMAIL);
            switch (response) {
                case Success:
                    addMessage(GestionController.SUCCESS_MESSAGE, "Votre compte a bien été créé");
                    if (setUserSession(user)) {
                        return "redirect:/projets";
                    } else {
                        addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la mise en session de votre compte");
                    }
                case Error:
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la création de votre compte");
                    break;
                default:
                    break;
            }

            return "home/register";
        } finally {
            gestionUser.close();
        }
    }

    private enumRegister registerUser(User user, boolean sendEmail) {
        try {
            if (user == null) {
                return enumRegister.Error;
            }

            user = gestionUser.register(user);
            if (user != null && gestionUser.commit()) {
                if (sendEmail) {
                    //On send un email de confirmation
                    GestionEmail gestionEmail = new GestionEmail();
                    if (gestionEmail.send(user.getEmail(), "Confirmation d'inscription",
                            "Veuillez confirmer votre inscription en cliquand sur : http://" + getHost() + ":" + getPort() + "/" + user.getId() + "/confirmation")) {
                        return enumRegister.Success;
                    } else {
                        return enumRegister.ErrorSendEmail;
                    }
                } else {
                    return enumRegister.Success;
                }
            } else {
                gestionUser.rollback();
            }

            return enumRegister.Error;
        } catch (Exception e) {
            return enumRegister.Error;
        }
    }

    @RequestMapping(value = "/{idUser}/confirmation", method = RequestMethod.GET)
    public String confirmation(@PathVariable(value = "idUser") int idUser, HttpServletRequest request) {
        EntityManager em = createEntityManager();
        gestionUser = new GestionUser(em);

        this.request = request;

        try {
            gestionUser.beginTransaction();

            User user = gestionUser.confirmation(idUser);
            if (user == null) {
                return "redirect:/";
            }

            if (gestionUser.commit()) {
                HttpSession session = request.getSession();
                if (session != null) {
                    //On ajoute l'id de l'user dans la session
                    session.setAttribute(USER_SESSION, user);

                    //On redirige l'user sur sa liste de projets
                    return "redirect:/projets";
                }
            }

            gestionUser.rollback();

            return "redirect:/";
        } finally {
            em.close();
        }
    }
}