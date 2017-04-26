package model.chat;

import java.io.Serializable;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class DonneeChat implements Serializable
{
    private int idSalon;
    private String email;
    private String message;

    public DonneeChat()
    {

    }

    public DonneeChat(int idSalon, String email, String message)
    {
        this();
        this.idSalon = idSalon;
        this.email = email;
        this.message = message;
    }

    public int getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
