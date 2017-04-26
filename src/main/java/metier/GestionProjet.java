package metier;

import controller.GestionController;
import dao.FichierDao;
import dao.LangageDao;
import dao.ProjetDao;
import dao.UserDao;
import model.*;
import viewModel.ViewModelDroitUserProjet;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public class GestionProjet extends GestionBase {
    private ProjetDao projetDao;
    private UserDao userDao;
    private FichierDao fichierDao;
    private GestionGit gestionGit;
    private LangageDao langageDao;
    private GestionDocker gestionDocker;
    private GestionDroitUserProjet gestionDroitUserProjet;

    public GestionProjet(EntityManager em) {
        super(em);
        projetDao = new ProjetDao(em);
        userDao = new UserDao(em);
        fichierDao = new FichierDao(em);
        gestionGit = new GestionGit();
        gestionDocker = new GestionDocker();
        gestionDroitUserProjet = new GestionDroitUserProjet(em);
        langageDao = new LangageDao(em);
    }

    /**
     * Permet de récupérer la liste de tous les projets de l'user connecté
     * @param idUser - id de l'user connecté
     * @return - la liste des projets de l'user connecté
     */
    public List<Projet> getAllProjetUser(int idUser) {
        try {
            return projetDao.findAll(idUser);
        } catch (Exception e) {
            return new ArrayList<Projet>();
        }
    }

    /**
     * Permet d'ajouter un projet
     * @param projet - le projet à ajouter
     * @param idUser - id de l'user connecté
     * @return - le projet ajouté
     */
    public Projet addProjet(Projet projet, int idUser) {
        try {
            if (idUser < 1 || projet == null) return null;

            //On regarde si un projet porte le même nom
            if (projetDao.existProjet(projet.getName(), idUser)) return null;

            //On récup l'user en session
            User user = userDao.findById(idUser);
            if (user == null) return null;

            Langage langage = langageDao.get(projet.getIdLangage());

            if(langage == null) return null;

            langage.getProjets().add(projet);
            projet.setLangage(langage);
            //On ajoute mutuellement l'user au projet
            if (!projet.addUser(user) || !user.addProjet(projet)) return null;

            //Création du dépôt Git
            if(!gestionGit.gitNew(projet)) return null;

            if (GestionController.ACTIVATE_DOCKER) {
                    //Création du docker lié au projet
                if (!gestionDocker.dockerNew(projet)) return null;
            }

            //On ajoute le projet en bdd
            if(!projetDao.add(projet, user))
            {
                return null;
            }

            //On récup tous les droits en bdd
            List<Droit> droits = gestionDroitUserProjet.getAllDroits();

            //On ajoute tous les droits à l'user pour le projet
            gestionDroitUserProjet.manageDroitUser(projet.getId(), idUser, new ViewModelDroitUserProjet(droits));

            return projet;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de récupérer un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - le projet
     */
    public Projet getProjet(int idProjet, int idUser)
    {
        try
        {
            return projetDao.findById(idProjet, idUser);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet d'ajouter un user à un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param idCollaborateur - id de l'user a ajouter
     * @return - succés si user ajouté au projet
     */
    public boolean addUserProjet(int idProjet, int idUser, int idCollaborateur)
    {
        try
        {
            //On regarde si l'user n'a pas déjà le projet
            if(userDao.haveProjet(idCollaborateur, idProjet)) return false;

            //On récup l'user
            User user = userDao.findById(idCollaborateur);
            if(user == null) return false;

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if(projet == null) return false;

            //On ajoute l'user au projet
            projet.getUsers().add(user);

            //On ajoute le projet à l'user
            user.addProjet(projet);

            //On persist
            em.persist(user);
            em.persist(projet);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de supprimer un users d'un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user à supprimer
     * @return - succés si user supprimé du projet
     */
    public boolean deleteUserProjet(int idProjet, int idUser)
    {
        try
        {
            if(idProjet < 1 || idUser < 1) return false;

            //On récup l'user
            User user = userDao.findById(idUser);
            if(user == null) return false;

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if(projet == null) return false;

            //On récup les droits de l'user sur le projet
            ProjetDroit projetDroit = null;
            for(ProjetDroit pr : user.getProjetDroits())
            {
                if(pr == null) continue;
                if(!pr.getProjet().equals(projet)) continue;

                projetDroit = pr;
                break;

            }

            //Si on a trouvé une relation de droit, on la supprime
            if(projetDroit != null)
            {
                user.getProjetDroits().remove(projetDroit);
                projet.getProjetDroits().remove(projetDroit);
                em.remove(projetDroit);
            }

            //On supprime la relation
            projet.getUsers().remove(user);
            user.getProjets().remove(projet);

            //On persist
            em.persist(user);
            em.persist(projet);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de récupérer la liste de tous les users du projet
     * @param idProjet - id du projet
     * @return - la liste de tous les users du projet
     */
    public List<User> getAllUsersProjet(int idProjet)
    {
        try
        {
            return projetDao.getAllUsers(idProjet);
        }
        catch(Exception e)
        {
            return new ArrayList<User>();
        }
    }

    /**
     * Permet de récupérer tous les users du projets sans l'user connecté
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - la liste des users du projet sans l'user connecté
     */
    public List<User> getAllUsersProjetWithoutCurrentUser(int idProjet, int idUser)
    {
        try
        {
            return projetDao.getAllUsersWithoutCurrentUser(idProjet, idUser);
        }
        catch(Exception e)
        {
            return new ArrayList<User>();
        }
    }

    /**
     * Permet de supprimer un projet
     * @param idProjet - id du projet à supprimer
     * @param idUser - id de l'user connecté
     * @return - succes si projet supprimée
     */
    public boolean deleteProjet(int idProjet, int idUser,boolean admin)
    {
        try
        {
            if(idProjet < 1 || idUser < 1) return false;

            //On regarde si l'user fait parti du projet
            if(!userDao.haveProjet(idUser, idProjet)) return false;

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if(projet == null) return false;

            //on remove tous les Users du projet
            /*for(User u : projet.getUsers())
            {
                u.getProjets().remove(projet);
            }*/
            if(admin){
                //on remove tous les Users du projet
                for(User u : projet.getUsers())
                {
                    u.getProjets().remove(projet);
                }

                //On remove le projet
                if(!projetDao.remove(projet)) return false;
                //On remove le repo
                if(!gestionGit.remove(projet)) return false;
            }else{
                //On récupère user
                User user = userDao.findById(idUser);

                //on remove user du projet
                user.getProjets().remove(projet);

            }


            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
/*
    public List<String> getAllMainFiles(int idProjet,int idUser){
        fichierDao.getAllMainFichiers()
    }*/
}
