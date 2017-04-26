package servlet;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.GestionController;
import metier.GestionGit;
import model.socketEditionFichier.FileChange;
import model.socketEditionFichier.FileContent;
import model.socketEditionFichier.FilePath;
import model.socketEditionFichier.RoomEditFile;
import utilitaires.UtilitairesString;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 17/10/2016.
 */
@WebServlet(urlPatterns = "/editionProjet")
public class EditionFichierSocket extends HttpServlet
{
    SocketIOServer socketIOServer = null;
    private List<RoomEditFile> rooms;
    GestionGit gestionGit = new GestionGit();

    public void init(ServletConfig config) throws ServletException {
        if(socketIOServer == null)
        {
            rooms = new ArrayList<>();
            Configuration configuration = new Configuration();
            configuration.setHostname(GestionController.getHost());
            configuration.setPort(27018);

            socketIOServer = new SocketIOServer(configuration);

            socketIOServer.addEventListener("setupIdFile", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                    RoomEditFile r = null;
                    //On cherche la room d'édition
                    Gson gson = new GsonBuilder().create();

                    FilePath fichier = gson.fromJson(json, FilePath.class);

                    if(fichier.getOldId() != 0){
                        for(RoomEditFile room : rooms){
                            if(room.getIdFichier() == fichier.getOldId()){
                                room.removeClient(socketIOClient);
                                if(room.isModified()) {
                                    System.out.println("hello" + room.getFilePath());
                                    gestionGit.updateFichier(room.getFilePath(), room.getContentFile(), room.getIdFichier());
                                    room.setModified(false);
                                }
                                if (room.getUsers().isEmpty()){
                                    room = null;
                                }
                                break;
                            }
                        }
                    }

                    for(RoomEditFile room : rooms)
                    {
                        if(room.getIdFichier() == fichier.getIdFichier())
                            r = room;

                        if(r != null)
                            break;
                    }

                    //Si on l'a pas trouvée, on crée la room d'édition
                    if(r == null)
                    {
                        r = new RoomEditFile(fichier.getIdFichier(),fichier.getPathFichier());
                        r.setContentFile(gestionGit.getContentFile(fichier.getPathFichier()));
                    }

                    //On ajoute l'user à la room
                    r.addClient(socketIOClient);

                    for(SocketIOClient client : r.getUsers())
                    {
                        client.sendEvent("updateuser",r.getUsers().size());
                    }
                    //On ajoute la romm à la liste
                    rooms.add(r);

                    FileContent fileContent = new FileContent(fichier.getIdFichier(),r.getContentFile());
                    String contentJson = gson.toJson(fileContent);
                    socketIOClient.sendEvent("canprogress", contentJson);
                }
            });

            socketIOServer.addEventListener("change", String.class,
                    new DataListener<String>() {
                        @Override
                        public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception
                        {
                            /*//On convertit le json en Objet
                            Gson gson = new GsonBuilder().create();
                            ConfigEditionFileSocket config = gson.fromJson(json, ConfigEditionFileSocket.class);


                            //socketIOServer.getBroadcastOperations().sendEvent("message", s);*/
                            Gson gson = new GsonBuilder().create();
                            System.out.print(json);
                            FileChange fileChange = gson.fromJson(json,FileChange.class);
                            System.out.println(fileChange.getIdFichier());
                            //On cherche la room du fichier
                            RoomEditFile room = null;for(RoomEditFile r : rooms)
                            {
                                if(r.getIdFichier() == fileChange.getIdFichier())
                                {
                                    room = r;
                                    room.setModified(true);
                                    room.setContentFile(UtilitairesString.subStringReplace(room.getContentFile(),fileChange.getText(),fileChange.getRemoved(),fileChange.getFromLine(),fileChange.getFromCh(),fileChange.getToLine(),fileChange.getToCh()));
                                    System.out.println(room.getContentFile());
                                    break;
                                }
                            }

                            if(room == null) return;
                            for(SocketIOClient client : room.getUsers())
                            {
                                if(client != socketIOClient){
                                    client.sendEvent("change",fileChange.getJson());
                                }
                            }
                        }
                    });


            socketIOServer.addEventListener("update", Integer.class, new DataListener<Integer>() {
                @Override
                public void onData(SocketIOClient socketIOClient, Integer idFichier, AckRequest ackRequest) throws Exception {
                    RoomEditFile r = null;
                    for(RoomEditFile room : rooms ){
                        if(room.getIdFichier() == idFichier){
                            if(room.isModified()) {
                                gestionGit.updateFichier(room.getFilePath(), room.getContentFile(), room.getIdFichier());
                                room.setModified(false);
                            }
                            break;
                        }
                    }
                }

            });
            socketIOServer.addDisconnectListener(new DisconnectListener() {
                @Override
                public void onDisconnect(SocketIOClient socketIOClient) {
                    //On parcourt toutes les rooms pour supprimer le client
                    RoomEditFile r = null;
                    for(RoomEditFile room : rooms ){
                        if(room.removeClient(socketIOClient)){
                            for (SocketIOClient client : room.getUsers()) {
                                client.sendEvent("updateuser",room.getUsers().size());
                            }
                            if (room.getUsers().isEmpty()){
                                System.out.println("bla");
                                r = room;
                                if(room.isModified()) {
                                    gestionGit.updateFichier(room.getFilePath(), room.getContentFile(), room.getIdFichier());
                                    room.setModified(false);
                                }
                            }
                            break;
                        }
                    }
                    if(r != null)
                        rooms.remove(r);
                }
            });
            socketIOServer.start();
        }
    }

    public void destroy() {
        socketIOServer.stop();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/projet/resume.jsp").forward(request, response);
    }

}
