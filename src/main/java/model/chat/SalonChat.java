package model.chat;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class SalonChat
{
    private int id;
    private RoomChat roomChat;

    public SalonChat(int id)
    {
        this.id = id;
        setRoomChat(new RoomChat());
    }

    public boolean itsMe(int id)
    {
        try
        {
            if(id < 1) return false;

            return this.id == id;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean addClient(UserChat client)
    {
        try
        {
            return roomChat.addClient(client);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean haveClient(UserChat client)
    {
        try
        {
            return roomChat.haveClient(client);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean haveClient(SocketIOClient client)
    {
        try
        {
            return roomChat.haveClient(client);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean removeClient(UserChat client) {
        try
        {
            return roomChat.removeClient(client);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeClient(SocketIOClient client) {
        try
        {
            return roomChat.removeClient(client);
        } catch (Exception e) {
            return false;
        }
    }

    public UserChat getClient(SocketIOClient client)
    {
        try
        {
            return roomChat.getClient(client);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public List<MessageUser> getLastMessages()
    {
        try
        {
            return roomChat.getLastMessages();
        }
        catch(Exception e)
        {
            return new ArrayList<MessageUser>();
        }
    }

    public boolean addMessage(UserChat userChat, String message)
    {
        try
        {
            if(userChat == null || message == null || message.isEmpty()) return false;

            return roomChat.addMessage(userChat, message);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean broadcastMessage(DonneeChat donneeChat)
    {
        try
        {
            return roomChat.broadcastMessage(donneeChat);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean isEmpty()
    {
        return roomChat.isEmpty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomChat getRoomChat() {
        return roomChat;
    }

    public void setRoomChat(RoomChat roomChat) {
        this.roomChat = roomChat;
    }
}
