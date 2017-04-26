package model.compilation;

import metier.GestionDocker;
import metier.GestionGit;
import model.Projet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naeno on 24/11/2016.
 */
public class CppCompiler implements ICompiler {

    private GestionGit gestionGit;
    private GestionDocker gestionDocker;
    public CppCompiler(){
        gestionGit = new GestionGit();
    }

    @Override
    public String compile(Projet projet) {
        String path = gestionGit.getPathProjet(projet);
        StringBuilder builder = new StringBuilder();
        try {
            //gestionDocker=new GestionDocker();
            //Process p = gestionDocker.executeJar(jarName,projet);
            /*ProcessBuilder pb = new ProcessBuilder("java","-Djava.security.manager","-jar", jarName);
            */
            ArrayList<String> files = new ArrayList<>(gestionGit.getArborescenceAbsolute(projet));
            files.add(0,projet.getName());
            files.add(0,"-o");
            files.add(0,"g++");
            ProcessBuilder pb = new ProcessBuilder(files);
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

    @Override
    public String execute(Projet projet, String mainFile) {
        String path = gestionGit.getPathProjet(projet);
        StringBuilder builder = new StringBuilder();
        try {
            gestionDocker=new GestionDocker();
            List<String> cmd=new ArrayList<>();
            cmd.add("./"+projet.getName());
            //Process p = gestionDocker.execute(jarName,projet);
            /*ProcessBuilder pb = new ProcessBuilder("java","-Djava.security.manager","-jar", jarName);
            */
            ProcessBuilder pb = gestionDocker.execute(cmd,projet);
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
