package controller;

import model.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GestionController
{
    //Variables static de config
    public static final String USER_SESSION = "USER_SESSION";
    public static final String DROIT_SESSION = "DROIT_SESSION";
    private static String host;
    private static String port;
    public static final boolean DEBUG = true;
    public static final boolean ACTIVATE_DOCKER = false;
    public static final boolean ACTIVATE_CONFIRMATION_EMAIL = false;
    protected static final String SUCCESS_MESSAGE = "success";
    protected static final String ERROR_MESSAGE = "error";

    //Variables qui servent dans tous les controleurs
    protected HttpServletRequest request;
    protected Model model;
    protected RedirectAttributes redirectAttributes;

    public GestionController()
    {
        if (DEBUG)
        {
            setHost("localhost");
            setPort("8080");
        }
        else
        {
            setHost("192.168.77.104");
            setPort("8080");
        }
    }

    public GestionController(HttpServletRequest request)
    {
        this();
        this.request = request;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        GestionController.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        GestionController.port = port;
    }

    /**
     * Permet de sauvegarder un message qui sera affiché dans la page jsp de retour
     * @param key - la clef
     * @param message - le message
     * @return - succès de l'ajout du message
     */
    public boolean addMessage(String key, String message)
    {
        try
        {
            if (key == null || key.isEmpty() || model == null) {
                return false;
            }

            if (message == null || message.isEmpty()) {
                return false;
            }

            redirectAttributes.addFlashAttribute(key, message);
            model.addAttribute(key, message);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de récupérer l'id de l'user connecté
     * @return - id de l'user connecté
     */
    public int getIdUser()
    {
        try
        {
            User user = getUserSession();
            if (user == null) {
                return -1;
            }
            return user.getId();
        }
        catch(Exception e)
        {
            return -1;
        }
    }

    public String getImageUser()
    {
        try
        {
            User user = getUserSession();
            if (user == null) {
                return null;
            }

            return user.getImage();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de récupérer l'user connecté en session
     * @return - l'User connecté
     */
    protected User getUserSession()
    {
        try
        {
            HttpSession session = request.getSession();
            return (User) session.getAttribute(USER_SESSION);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet d'ajouter un objet User en session
     * @param user - l'user à mettre en Session
     * @return - succès de la mise en session
     */
    protected boolean setUserSession(User user)
    {
        try
        {
            if (user == null || user.getId() < 1) {
                return false;
            }

            HttpSession session = request.getSession();
            if(session != null)
            {
                //On ajoute l'id de l'user dans la session
                session.setAttribute(USER_SESSION, user);

                return true;
            }

            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de créer une conneion à la bdd
     * @return - l'entityManager
     */
    public static EntityManager createEntityManager()
    {
        return Persistence.createEntityManagerFactory("localEntity").createEntityManager();
    }
}
