package model.socketEditionFichier;

import com.corundumstudio.socketio.SocketIOClient;
import model.socket.Room;

import java.util.List;

/**
 * Created by pierremarsot on 18/10/2016.
 */
public class RoomEditFile extends Room<SocketIOClient>
{
    private int idFichier;

    private String contentFile;

    private boolean modified;

    private String filePath;
    public RoomEditFile()
    {

    }

    public RoomEditFile(int idFichier,String filePath)
    {
        this();
        this.setIdFichier(idFichier);
        this.setFilePath(filePath);
        contentFile = null;
        modified = false;
    }

    public boolean removeClient(SocketIOClient user)
    {
        try
        {
            return (user == null) ? false : getUsers().remove(user);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }

    public List<SocketIOClient> getUsers() {
        return users;
    }

    public void setUsers(List<SocketIOClient> users) {
        this.users = users;
    }


    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
