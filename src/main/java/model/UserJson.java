package model;

import java.io.Serializable;

/**
 * Created by pierremarsot on 14/10/2016.
 */
public class UserJson implements Serializable
{
    public int id;
    public String email;

    public UserJson(int id, String email)
    {
        this.id = id;
        this.email = email;
    }
}
