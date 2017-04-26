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
import metier.GestionChat;
import model.chat.DonneeChat;
import model.chat.MessageUser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 17/10/2016.
 */
public class ChatSocket extends HttpServlet
{
    SocketIOServer socketIOServer = null;
    private GestionChat gestionChat;

    @Override
    public void init(ServletConfig config) throws ServletException {
        if(socketIOServer == null)
        {
            gestionChat = new GestionChat();

            Configuration configuration = new Configuration();
            configuration.setHostname(GestionController.getHost());
            configuration.setPort(27017);

            socketIOServer = new SocketIOServer(configuration);

            socketIOServer.addEventListener("getSalons", null, new DataListener<Object>() {
                @Override
                public void onData(SocketIOClient socketIOClient, Object o, AckRequest ackRequest) throws Exception {

                }
            });

            socketIOServer.addEventListener("joinSalon", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
                    //On parse le json reçu en objet
                    if(gestionChat.joinSalon(client, s))
                        client.sendEvent("canJoinSalon");
                }
            });

            socketIOServer.addEventListener("leaveSalon", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient client, String s, AckRequest ackRequest) throws Exception {
                    if(gestionChat.leaveSalon(client, s))
                        client.sendEvent("leaveClear");
                }
            });

            socketIOServer.addEventListener("getLastMessages", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

                    List<MessageUser> messageUsers = gestionChat.getLastMessages(s);
                    if(messageUsers == null || messageUsers.isEmpty()) return;

                    Gson gson = new GsonBuilder().create();

                    List<DonneeChat> donneeChats = new ArrayList<DonneeChat>();
                    for(MessageUser m : messageUsers)
                    {
                        donneeChats.add(new DonneeChat(0, m.getPseudoClient(), m.getMessage()));
                    }

                    String response = gson.toJson(donneeChats);
                    socketIOClient.sendEvent("setLastMessages", response);
                }
            });

            socketIOServer.addEventListener("sendMessage", String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                    gestionChat.addMessage(socketIOClient, s);
                }
            });

            socketIOServer.addDisconnectListener(new DisconnectListener() {
                @Override
                public void onDisconnect(SocketIOClient client) {
                    gestionChat.leaveSalon(client);
                }
            });
            socketIOServer.start();
        }
    }

    @Override
    public void destroy() {
        socketIOServer.stop();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/projet/resume.jsp").forward(request, response);
    }
}
