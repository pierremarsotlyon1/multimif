package controller;

import metier.GestionDroitUserProjet;
import metier.GestionExport;
import model.Droit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pierremarsot on 15/11/2016.
 */
@Controller
public class ExportProjetController extends GestionController {
    private GestionExport gestionExport;
    private GestionDroitUserProjet gestionDroitUserProjet;
    protected static final Logger LOGGER = LoggerFactory.getLogger(ExportProjetController.class);

    @RequestMapping(value = "/projet/{idProjet}/export", method = RequestMethod.GET, produces = "application/zip")
    public void export(@PathVariable(value = "idProjet") int idProjet, HttpServletRequest request, Model model, HttpServletResponse response) {

        EntityManager em = createEntityManager();
        gestionExport = new GestionExport(em);
        gestionDroitUserProjet=new GestionDroitUserProjet(em);

        try
        {
            this.model = model;
            this.request = request;
            this.model = model;
            this.request = request;
            int idUser = getIdUser();
            List<Droit> list=gestionDroitUserProjet.getDroitsUser(idUser,idProjet);
            if(list.contains(new Droit(Droit.Export))) {

                //Création du zip
                byte[] bytes = gestionExport.exportProjet(idProjet);
                if (bytes == null || bytes.length == 0) {
                    // return "redirect:/projet/" + idProjet;
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addHeader("Content-Disposition", "attachment; filename=\"projet.zip\"");

                    try {
                        ServletOutputStream outStream = response.getOutputStream();
                        outStream.write(bytes);

                        outStream.flush();

                    } catch (IOException e) {
                        LOGGER.info(e.getMessage());
                    }
                }
            }
        }
        finally
        {
            gestionExport.close();
        }
    }
}
