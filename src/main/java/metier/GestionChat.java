package metier;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.chat.DonneeChat;
import model.chat.MessageUser;
import model.chat.SalonChat;
import model.chat.UserChat;

import java.util.ArrayList;
import java.util.List;

public class GestionChat
{
    private List<SalonChat> salons;

    public GestionChat()
    {
        salons = new ArrayList<SalonChat>();
    }

    /**
     * Permet à un client de rejoindre un salon
     * @param client - le socket du client
     * @param json - le json contenant le chat à rejoindre
     * @return - succés de l'ajout de l'user dans le salon
     */
    public boolean joinSalon(SocketIOClient client, String json)
    {
        try
        {
            if(client == null || json == null || json.isEmpty()) return false;

            DonneeChat donneeChat = getDonneeChat(json);
            if(donneeChat == null) return false;

            //On récup le salon
            SalonChat salonChat = getSalonById(donneeChat.getIdSalon());

            //Si le salon n'existe pas, on le crée
            if(salonChat == null)
            {
                salonChat = addSalon(donneeChat.getIdSalon());
                if(salonChat == null) return false;
            }

            //On ajoute le client au salon
            UserChat userChat = new UserChat(client, donneeChat.getEmail());
            if(!salonChat.addClient(userChat)) return false;

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de supprimer un client à un salon
     * @param client - le client à supprimer
     * @param json - le json content les infos du salon
     * @return - suppression du client au salon
     */
    public boolean leaveSalon(SocketIOClient client, String json)
    {
        try
        {
            if(client == null || json == null || json.isEmpty()) return false;

            DonneeChat donneeChat = getDonneeChat(json);
            if(donneeChat == null) return false;

            //On récup le salon
            SalonChat salonChat = getSalonById(donneeChat.getIdSalon());
            if(salonChat == null) return false;

            //On récup l'user lié au salon
            UserChat userChat = salonChat.getClient(client);
            if(userChat == null) return false;

            //On supprime le client du salon
            if(salonChat.removeClient(userChat)) return false;

            //On regarde si il faut supprimer le salon (plus d'user co)
            if(salonChat.isEmpty())
                removeSalon(salonChat.getId());

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de supprimer un client d'un salon sans connaître directement le salon
     * @param client - le client à supprimer
     * @return
     */
    public boolean leaveSalon(SocketIOClient client)
    {
        try
        {
            if(client == null) return false;

            //On parcourt la liste des salons pour trouver le salon dans lequel est le client
            SalonChat salonChat = null;
            for(SalonChat s : salons)
            {
                if(!s.haveClient(client)) continue;
                salonChat = s;
                break;
            }

            if(salonChat == null) return false;

            return salonChat.removeClient(client);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de récupérer les derniers messages du salon
     * @param json
     * @return
     */
    public List<MessageUser> getLastMessages(String json)
    {
        try
        {
            if(json == null || json.isEmpty()) return new ArrayList<MessageUser>();

            DonneeChat donneeChat = getDonneeChat(json);
            if(donneeChat == null) return new ArrayList<MessageUser>();

            //On récup le salon
            SalonChat salonChat = getSalonById(donneeChat.getIdSalon());
            if(salonChat == null) return new ArrayList<MessageUser>();

            return salonChat.getLastMessages();
        }
        catch(Exception e)
        {
            return new ArrayList<MessageUser>();
        }
    }

    /**
     * Permet d'ajouter un message à un salon
     * @param client - le client qui ajoute le message
     * @param json - json contenant les infos à ajouter
     * @return - succés de l'ajout du message
     */
    public boolean addMessage(SocketIOClient client, String json)
    {
        try
        {
            if(client == null || json == null || json.isEmpty()) return false;

            //On récup les données chat
            DonneeChat donneeChat = getDonneeChat(json);
            if(donneeChat == null) return false;

            //On récup le salon
            SalonChat salonChat = getSalonById(donneeChat.getIdSalon());
            if(salonChat == null) return false;

            //On récup l'user lié au salon
            UserChat userChat = salonChat.getClient(client);
            if(userChat == null) return false;

            //On ajoute le message au salon
            if(!salonChat.addMessage(userChat, donneeChat.getMessage())) return false;

            //On fait un broadcast du message à tous les clients du salon
            return salonChat.broadcastMessage(donneeChat);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet d'ajouter un salon
     * @param id - id du salon
     * @return - le salon ajouté
     */
    private SalonChat addSalon(int id)
    {
        try
        {
            if(id < 1) return null;

            //Création du salon
            SalonChat salonChat = new SalonChat(id);

            //Ajout du salon
            salons.add(salonChat);

            return salonChat;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de supprimer un salon
     * @param id - l'id du salon à supprimer
     * @return - succés de la suppression du salon
     */
    private boolean removeSalon(int id)
    {
        try
        {
            if(id < 1) return false;

            //On récup le salon
            SalonChat salon = getSalonById(id);
            if(salon == null) return false;

            //On supprime le salon
            salons.remove(salon);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de supprimer un salon via son id
     * @param id - id du salon
     * @return
     */
    private SalonChat getSalonById(int id)
    {
        try
        {
            if(id < 1) return null;

            for(SalonChat s : salons)
            {
                if(!s.itsMe(id)) continue;

                return s;
            }

            return null;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de parser le json en DonneeChat
     * @param json - le json à parser
     * @return - l'objet DonneeChat
     */
    private DonneeChat getDonneeChat(String json)
    {
        try
        {
            if(json == null || json.isEmpty()) return null;

            //On parse le json reçu en objet
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, DonneeChat.class);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
