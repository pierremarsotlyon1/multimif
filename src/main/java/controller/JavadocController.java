package controller;

import metier.GestionEditionProjet;
import metier.GestionJavaDoc;
import model.Fichier;
import model.JavaDocFichier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by pierremarsot on 19/11/2016.
 */
@Controller
public class JavadocController extends GestionController
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(JavadocController.class);

    @RequestMapping(value = "/projet/{idProjet}/docs", method = RequestMethod.GET)
    public String index(@PathVariable("idProjet") int idProjet, HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        GestionEditionProjet gestionEditionProjet = new GestionEditionProjet(em);

        try
        {
            this.model = model;
            this.request = request;

            List<Fichier> fichiers = gestionEditionProjet.getFichiersWithJavaDoc(idProjet, getIdUser());

            model.addAttribute("fichiers", fichiers);

            return "docs/index";
        }
        finally
        {
            gestionEditionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/docs/{idFichier}", method = RequestMethod.POST)
    @ResponseBody
    public String index(@PathVariable("idFichier") int idFichier)
    {
        EntityManager em = createEntityManager();
        GestionJavaDoc gestionJavaDoc = new GestionJavaDoc(em);

        try
        {
            try
            {
                JavaDocFichier javaDocFichier = gestionJavaDoc.getJavaDocFichierComplet(idFichier);
                if (javaDocFichier == null) {
                    return null;
                }

                return javaDocFichier.toString();
            }
            catch(Exception e)
            {
                LOGGER.info(e.getMessage());
                return null;
            }
        }
        finally
        {
            gestionJavaDoc.close();
        }
    }
}
