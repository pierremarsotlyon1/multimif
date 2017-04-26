package model.compilation;

import metier.GestionGit;
import model.Projet;

import java.io.*;

/**
 * Created by Naeno on 24/11/2016.
 */
public class PythonCompiler implements ICompiler{

    private GestionGit gestionGit;

    public PythonCompiler(){
        gestionGit = new GestionGit();
    }

    @Override
    public String compile(Projet projet) {
        return "Pas besoin de compilation";
    }


    //Implementer le docker
    @Override
    public String execute(Projet projet, String mainFile) {
        String path = gestionGit.getPathProjet(projet);
        StringBuilder builder = new StringBuilder();
        try {
            //gestionDocker=new GestionDocker();
            //Process p = gestionDocker.executeJar(jarName,projet);
            /*ProcessBuilder pb = new ProcessBuilder("java","-Djava.security.manager","-jar", jarName);
            */
            ProcessBuilder pb = new ProcessBuilder("python",mainFile + ".py");
            pb.directory(new File(path));
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
            e.printStackTrace();
        }
        return builder.toString();
    }
}
