package model.chat;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class UserChat
{
    private SocketIOClient socket;
    private String pseudo;

    public UserChat(SocketIOClient client, String pseudo)
    {
        this.socket = client;
        this.pseudo = pseudo;
    }

    public SocketIOClient getSocket() {
        return socket;
    }

    public void setSocket(SocketIOClient socket) {
        this.socket = socket;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
