package model.chat;

import java.io.Serializable;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class MessageUser implements Serializable
{
    private String pseudoClient;
    private String message;

    public MessageUser(String pseudoClient, String message)
    {
        this.setPseudoClient(pseudoClient);
        this.setMessage(message);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPseudoClient() {
        return pseudoClient;
    }

    public void setPseudoClient(String pseudoClient) {
        this.pseudoClient = pseudoClient;
    }
}
