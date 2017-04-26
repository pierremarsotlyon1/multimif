package controller;

import com.google.gson.Gson;
import metier.GestionDocker;
import metier.GestionEditionProjet;
import metier.GestionGit;
import metier.GestionProjet;
import model.Projet;
import model.User;
import model.compilation.CppCompiler;
import model.compilation.ICompiler;
import model.compilation.JavaCompilateur;
import model.compilation.PythonCompiler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Naeno on 21/11/2016.
 */
@Controller
public class ExecutionController extends GestionController {


    private GestionGit gestionGit = new GestionGit();
    private GestionDocker gestionDocker = new GestionDocker();
    private EntityManager em = GestionController.createEntityManager();
    private GestionProjet gestionProjet = new GestionProjet(em);
    private GestionEditionProjet gestionEditionProjet;
    private ICompiler compiler;

    @RequestMapping(value = "/projet/{idProjet}/execution", method = RequestMethod.POST)
    public @ResponseBody
    String execProject(@RequestParam String mainFile, @PathVariable(value = "idProjet") int idProjet,
                       HttpServletRequest request, Model model)
    {
        System.out.println(mainFile);
        int idUser = ((User) request.getSession().getAttribute(GestionController.USER_SESSION)).getId();
        Projet projet = gestionProjet.getProjet(idProjet,idUser);
        /*String jarName = projet.getName()+".jar";
        String path = gestionGit.getPathRootGit() + "bins/" + projet.getName();
        buildJar(path,jarName,mainFile);*/
        switch (projet.getLangage().getId()){
            case(1):
                compiler = new JavaCompilateur();
                break;
            case(2):
                compiler = new PythonCompiler();
                break;
            case(3):
                compiler = new CppCompiler();
                break;
            default:
                break;
        }
        return compiler.execute(projet,mainFile);
    }

    @RequestMapping(value = "/projet/{idProjet}/execlist", method = RequestMethod.POST)
    public @ResponseBody
    String getMainFiles(@PathVariable(value = "idProjet") int idProjet,
                        HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        gestionEditionProjet = new GestionEditionProjet(em);

        try
        {
            this.model = model;
            this.request = request;

            //On récup l'id de l'user co
            int idUser = getIdUser();

            //On récup l'arborescence du projet
            Gson gson = new Gson();
            String response = gson.toJson(gestionEditionProjet.getAllMainFichiers(idProjet,idUser));

            return response;
        }
        finally
        {
            gestionEditionProjet.close();
        }
    }
}
