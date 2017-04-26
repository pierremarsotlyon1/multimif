package model.chat;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.socket.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class RoomChat extends Room<UserChat>
{
    private static int nbLastMessages = 20;
    private List<MessageUser> messageUsers;
    protected static final Logger LOGGER = LoggerFactory.getLogger(RoomChat.class);

    public RoomChat()
    {
        setMessageUsers(new ArrayList<MessageUser>());
    }

    public boolean addMessage(UserChat userChat, String message)
    {
        try
        {
            if (userChat == null || message == null || message.isEmpty()) {
                return false;
            }

            messageUsers.add(new MessageUser(userChat.getPseudo(), message));

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public List<MessageUser> getLastMessages()
    {
        try
        {
            if (messageUsers == null || messageUsers.isEmpty()) {
                return new ArrayList<>();
            }

            if(messageUsers.size() >= nbLastMessages)
                return messageUsers.subList(0, nbLastMessages);
            return messageUsers.subList(0, messageUsers.size());
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    public UserChat getClient(SocketIOClient client)
    {
        try
        {
            if (client == null) {
                return null;
            }

            for(UserChat u : users)
            {
                if (!u.getSocket().equals(client)) {
                    continue;
                }
                return u;
            }

            return null;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    public boolean broadcastMessage(DonneeChat donneeChat)
    {
        try
        {
            if (donneeChat == null) {
                return false;
            }

            for(UserChat userChat : users)
            {
                SocketIOClient client = userChat.getSocket();
                if (client == null) {
                    continue;
                }

                Gson gson = new GsonBuilder().create();
                client.sendEvent("receiveMessage", gson.toJson(donneeChat));
            }

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean haveClient(SocketIOClient client)
    {
        try
        {
            if (client == null) {
                return false;
            }

            for(UserChat u : users)
            {
                if (u.getSocket() == client) {
                    return true;
                }
            }

            return false;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean removeClient(SocketIOClient client)
    {
        try
        {
            if (client == null) {
                return false;
            }

            UserChat userChat = null;
            for(UserChat u : users)
            {
                if (u.getSocket() != client) {
                    continue;
                }
                userChat = u;
                break;
            }

            if (userChat == null) {
                return false;
            }

            users.remove(userChat);

            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public List<MessageUser> getMessageUsers() {
        return messageUsers;
    }

    public void setMessageUsers(List<MessageUser> messageUsers) {
        this.messageUsers = messageUsers;
    }
}
