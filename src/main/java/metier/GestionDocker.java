package metier;

import model.Projet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilitaires.UtilitairesString;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionDocker {

    // pierre : /Library/Java/JavaVirtualMachines/jdk1.8.0_77.jdk/Contents/Home
    // vm : /usr/lib/jvm/java-1.8.0-openjdk-amd64
    public static final String JDK_PATH = "/usr/lib/jvm/jdk1.8.0_101";

    //Pierre : /Users/pierremarsot/Downloads/mavenmultimif/
    //VM : /usr/share/maven
    public static final String MVN_PATH = "/usr/share/maven/";
    protected static final Logger LOGGER = LoggerFactory.getLogger(GestionDocker.class);

    public boolean dockerNew(Projet projet) {

        try
        {
            ProcessBuilder pb;
            Process p;
            InputStream is;
            InputStreamReader isr;
            BufferedReader br;
            String ligne;
            StringBuilder builder;
            try (PrintWriter writer = new PrintWriter(GestionGit.localPath + projet.getName() + "/Dockerfile", "UTF-8")) {
                writer.println("FROM debian:jessie");

                writer.println("# -----------------------------------------------------------------------------\n" +
                        "# Configuration du proxy pour fonctionnement en interne à l'UCBL\n" +
                        "# -----------------------------------------------------------------------------\n" +
                        "RUN echo \"\\\n" +
                        "export http_proxy=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export ftp_proxy=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export https_proxy=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export all_proxy=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export HTTP_PROXY=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export FTP_PROXY=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export HTTPS_PROXY=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "export ALL_PROXY=http://proxy.univ-lyon1.fr:3128;\\\n" +
                        "\" >> /etc/bash.bashrc\n" +
                        "\n" +
                        "RUN echo 'Acquire::http::Proxy \"http://proxy.univ-lyon1.fr:3128\";' > /etc/apt/apt.conf.d/99proxy\n");

                writer.println("VOLUME " + JDK_PATH);

                writer.println("VOLUME " + MVN_PATH);

                writer.println("RUN useradd  -g users -d /home/user1 -s /bin/bash -p 'user1' user1");

                writer.println("VOLUME /home/user1/git/bins/" + projet.getName());

                writer.println("WORKDIR /home/user1/git/bins/");

                writer.println("RUN chown user1:users -R /home/user1");

                writer.println("RUN chown user1:users -R /home/user1/git/bins/");


                writer.close();
            }

            pb = new ProcessBuilder(
                    "docker",
                    "build",
                    "-t",
                    "docker-" + UtilitairesString.format(projet.getName()),
                    GestionGit.localPath + projet.getName()
            );
            pb.directory(new File("./"));
            pb.redirectErrorStream(true);
            p = pb.start();

            is = p.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            builder = new StringBuilder();

            while (( ligne = br.readLine()) != null) {
                builder.append(ligne);
                builder.append("\n");
                LOGGER.info(ligne);
                // ligne contient une ligne de sortie normale ou d'erreur
            }
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public ProcessBuilder execute(List<String> cmdList, Projet projet) {
        ArrayList<String> cmd=new ArrayList<>();
        cmd.add("docker");
        cmd.add("run");
        cmd.add("-v");
        cmd.add(JDK_PATH +":"+ JDK_PATH);
        cmd.add("-v");
        cmd.add(MVN_PATH +":"+ MVN_PATH);
        cmd.add("-v");
        cmd.add(GestionGit.localPath+"bins/"+projet.getName()+":/home/user1/git/bins/"+projet.getName());
        cmd.add("-tu");
        cmd.add("user1");
        cmd.add("docker-"+ UtilitairesString.format(projet.getName()));

        for (String str:cmdList){
            cmd.add(str);
        }
        ProcessBuilder pb= new ProcessBuilder(cmd);
        System.out.println(pb.command().toString());

        return pb;
    }

}


