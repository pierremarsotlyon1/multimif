package controller;

import metier.GestionEditionProjet;
import model.Dossier;
import model.Fichier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import viewModel.CreateElementProjet;
import viewModel.DeleteFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by pierremarsot on 14/10/2016.
 */
@Controller
public class EditionProjetController extends GestionController {
    private GestionEditionProjet gestionEditionProjet;

    @RequestMapping(value = "/projet/{idProjet}/getArborescence", method = RequestMethod.POST)
    public
    @ResponseBody
    String getArborescenceProjet(@PathVariable(value = "idProjet") int idProjet,
                                 HttpServletRequest request, Model model) {
        EntityManager em = createEntityManager();
        gestionEditionProjet = new GestionEditionProjet(em);

        try {
            this.model = model;
            this.request = request;

            //On récup l'id de l'user co
            int idUser = getIdUser();

            //On récup l'arborescence du projet
            Dossier root = gestionEditionProjet.getContenu(idProjet, idUser);
            if (root == null) return null;

            //On parse l'arborescence du projet en String pour que la vue puisse l'afficher
            String r = root.createArborescence();

            return r;
        } finally {
            gestionEditionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/addElement", method = RequestMethod.POST)
    public String createElementProjet(@ModelAttribute("createElementProjet") CreateElementProjet createElementProjet,
                                      @PathVariable(value = "idProjet") int idProjet,
                                      HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        EntityManager em = createEntityManager();
        gestionEditionProjet = new GestionEditionProjet(em);

        try {
            this.request = request;
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            if (createElementProjet != null) {
                //On regarde si on vient du dossier root
                boolean dossierRoot = "1".equals(createElementProjet.getDossierRoot()) ? true : false;

                gestionEditionProjet.beginTransaction();

                //On regarde si on crée un dossier ou fichier
                int type = Integer.parseInt(createElementProjet.getType());
                switch (type) {
                    //Fichier
                    case 1:
                        //Création du fichier
                        Fichier fichier = new Fichier();
                        fichier.setName(createElementProjet.getName());
                        fichier.setMain(createElementProjet.getMain());
                        //Ajout du fichier
                        fichier = gestionEditionProjet.addFichier(fichier, Integer.parseInt(createElementProjet.getIdDossier()), idProjet,
                                getIdUser(), dossierRoot);
                        if (fichier != null && gestionEditionProjet.commit()) {
                            addMessage(GestionController.SUCCESS_MESSAGE, "Le fichier a bien été ajouté");
                        } else {
                            gestionEditionProjet.rollback();
                            addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de l'ajout du fichier");
                        }
                        break;

                    //Dossier
                    case 2:
                        //Création du dossier
                        Dossier dossier = new Dossier(createElementProjet.getName());

                        //Ajout du dossier
                        dossier = gestionEditionProjet.addDossier(dossier, Integer.parseInt(createElementProjet.getIdDossier()),
                                idProjet, getIdUser(), dossierRoot);
                        if (dossier != null && gestionEditionProjet.commit()) {
                            addMessage(GestionController.SUCCESS_MESSAGE, "Le dossier a bien été créé");
                        } else {
                            gestionEditionProjet.rollback();
                            addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la création du dossier");
                        }
                        break;
                }

            }

            return "redirect:/projet/" + idProjet;
        } finally {
            gestionEditionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/deleteFile/{idFichier}", method = RequestMethod.POST)
    public @ResponseBody DeleteFile deleteFile(@PathVariable(value = "idProjet") int idProjet,
                             @PathVariable(value = "idFichier") int idFichier,
                             HttpServletRequest request)
    {
        try
        {
            if(idFichier < 1 || idProjet < 1) return null;

            EntityManager em = createEntityManager();
            gestionEditionProjet = new GestionEditionProjet(em);

            gestionEditionProjet.beginTransaction();

            boolean success = gestionEditionProjet.deleteFichier(idProjet, idFichier);
            if(success && gestionEditionProjet.commit())
            {
                return new DeleteFile(true, "Le fichier a bien été supprimé");
            }
            else
            {
                gestionEditionProjet.rollback();
            }

            return new DeleteFile(false, "Erreur lors de la suppression du fichier");
        }
        finally
        {
            gestionEditionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/setMain/{idFichier}", method = RequestMethod.POST)
    public @ResponseBody DeleteFile setMain(@PathVariable(value = "idProjet") int idProjet,
                                               @PathVariable(value = "idFichier") int idFichier)
    {
        try
        {
            if(idFichier < 1 || idProjet < 1) return null;

            EntityManager em = createEntityManager();
            gestionEditionProjet = new GestionEditionProjet(em);

            gestionEditionProjet.beginTransaction();

            boolean success = gestionEditionProjet.setMainFichier(idProjet, idFichier);
            if(success && gestionEditionProjet.commit())
            {
                return new DeleteFile(true, "Le fichier est maintenant executable");
            }
            else
            {
                gestionEditionProjet.rollback();
            }

            return new DeleteFile(false, "Erreur lors du changement");
        }
        finally
        {
            gestionEditionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/setUnMain/{idFichier}", method = RequestMethod.POST)
    public @ResponseBody DeleteFile setUnMain(@PathVariable(value = "idProjet") int idProjet,
                                               @PathVariable(value = "idFichier") int idFichier)
    {
        try
        {
            if(idFichier < 1 || idProjet < 1) return null;

            EntityManager em = createEntityManager();
            gestionEditionProjet = new GestionEditionProjet(em);

            gestionEditionProjet.beginTransaction();

            boolean success = gestionEditionProjet.setUnMainFichier(idProjet, idFichier);
            if(success && gestionEditionProjet.commit())
            {
                return new DeleteFile(true, "Le fichier est maintenant non executable");
            }
            else
            {
                gestionEditionProjet.rollback();
            }

            return new DeleteFile(false, "Erreur lors du changement");
        }
        finally
        {
            gestionEditionProjet.close();
        }
    }

}
