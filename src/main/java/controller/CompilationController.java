package controller;

import metier.GestionGit;
import metier.GestionProjet;
import model.Projet;
import model.User;
import model.compilation.CppCompiler;
import model.compilation.ICompiler;
import model.compilation.JavaCompilateur;
import model.compilation.PythonCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Naeno on 22/11/2016.
 */
@Controller
public class CompilationController extends GestionController {

    private GestionGit gestionGit = new GestionGit();
    private EntityManager em = GestionController.createEntityManager();
    private GestionProjet gestionProjet = new GestionProjet(em);
    private ICompiler compiler;
    protected static final Logger LOGGER = LoggerFactory.getLogger(CompilationController.class);

    @RequestMapping(value = "/projet/{idProjet}/compilation", method = RequestMethod.POST)
    public @ResponseBody
    String compileProject(@PathVariable(value = "idProjet") int idProjet, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int idUser = ((User) req.getSession().getAttribute(GestionController.USER_SESSION)).getId();
        Projet projet = gestionProjet.getProjet(idProjet,idUser);
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
        String result = compiler.compile(projet);
        //res.sendRedirect("/projet/"+idProjet);
        return result;
        //req.getRequestDispatcher("/WEB-INF/pages/projet/resume.jsp").forward(req, res);
    }


    @RequestMapping(value = "/projet/{idProjet}/maven", method = RequestMethod.POST)
    public @ResponseBody
    String runMaven(@RequestParam String commands,@PathVariable(value = "idProjet") int idProjet, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int idUser = ((User) req.getSession().getAttribute(GestionController.USER_SESSION)).getId();
        Projet projet = gestionProjet.getProjet(idProjet,idUser);
        String path = gestionGit.getPathProjet(projet);
        String result = executeMaven(path,commands);
        //res.sendRedirect("/projet/"+idProjet);
        return result;
        //req.getRequestDispatcher("/WEB-INF/pages/projet/resume.jsp").forward(req, res);
    }

    private String executeMaven(String directory, String commands) {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> listCommands = new ArrayList<>(Arrays.asList(commands.split(" ")));
        listCommands.add(0,"mvn");
        try {
            ProcessBuilder pb = new ProcessBuilder(listCommands);
            pb.directory(new File(directory));
            pb.redirectErrorStream(true);
            Process p = pb.start();

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ligne;

            while (( ligne = br.readLine()) != null) {
                builder.append(ligne);
                builder.append("\n");
                System.out.println(ligne);
                // ligne contient une ligne de sortie normale ou d'erreur
            }

        }
        catch (IOException e){
            LOGGER.info(e.getMessage());
        }
        return builder.toString();
    }

    public void destroy() {
        em = null;
    }





}
