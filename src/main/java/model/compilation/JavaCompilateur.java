package model.compilation;

import metier.GestionDocker;
import metier.GestionGit;
import model.Projet;

import javax.tools.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static controller.GestionController.ACTIVATE_DOCKER;

/**
 * Created by Naeno on 24/11/2016.
 */
public class JavaCompilateur implements ICompiler {

    private GestionGit gestionGit;
    private GestionDocker gestionDocker;

    public JavaCompilateur(){
        gestionGit = new GestionGit();
        gestionDocker = new GestionDocker();
    }
    @Override
    public String compile(Projet projet) {
        List<String> files = gestionGit.getArborescenceAbsolute(projet);
        Iterator<String> itr = files.iterator();
        String path = gestionGit.getPathRootGit() + "bins/" + projet.getName();

        gestionGit.mkdirs(new File(path));
        while(itr.hasNext()){
            if(!itr.next().endsWith(".java"))
                itr.remove();
        }
        System.out.println(files);
        String[] compileOptions = new String[]{"-d", path} ;
        Iterable<String> compilationOptions = Arrays.asList(compileOptions);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromStrings(files);
        boolean compilationResult = compiler.getTask(null, fileManager, diagnosticsCollector, compilationOptions, null, compilationUnits).call();
        StringBuilder builder = new StringBuilder();
        if(compilationResult){

            System.out.println("Compilation is successful");
            builder.append("Compilation is successful\n");
        }else{
            List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
                // read error dertails from the diagnostic object
                builder.append(diagnostic);
                System.out.println(diagnostic);
            }
            builder.append("\nCompilation Failed");
            System.out.println("Compilation Failed");

        }
        return builder.toString().replaceAll(gestionGit.getPathProjet(projet)+"/","");
    }

    @Override
    public String execute(Projet projet, String mainFile) {
        StringBuilder builder = new StringBuilder();
        try {
            String path = gestionGit.getPathRootGit() + "bins/" + projet.getName();

            buildJar(path,projet.getName()+".jar",mainFile);

            ProcessBuilder pb;
            if(ACTIVATE_DOCKER)
            {
                gestionDocker=new GestionDocker();
                List<String>cmd=new ArrayList<>();
                cmd.add(GestionDocker.JDK_PATH +"/bin/java");
                cmd.add("-jar");
                cmd.add("/home/user1/git/bins/"+projet.getName()+"/"+projet.getName()+".jar");
                //JDK_PATH+"/bin/java","-jar", "/home/user1/git/bins/"+projet.getName()+"/"+jarName
                pb = gestionDocker.execute(cmd,projet);
            }
            else
            {
                pb = new ProcessBuilder("java","-Djava.security.manager","-jar", projet.getName() + ".jar");
            }

            if(pb != null)
            {
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
                    // ligne contient une ligne de sortie normale ou d'erreur  /usr/local/Cellar/tomcat/8.5.5/libexec/bin/git
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();    }


    private void buildJar(String directory,String jarName,String mainFile){
        try {
            ProcessBuilder pb = new ProcessBuilder("jar","cfe",jarName,mainFile,".");
            pb.directory(new File(directory));
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}

