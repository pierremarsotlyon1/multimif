package model.socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 20/10/2016.
 */
public class Room<T>
{
    protected List<T> users;

    public Room()
    {
        users = new ArrayList<T>();
    }

    public boolean addClient(T client)
    {
        try
        {
            if(client == null) return false;

            //On regarde si on a pas déjà l'user
            if(haveClient(client)) return false;

            users.add(client);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean removeClient(T client)
    {
        try
        {
            if(client == null) return false;

            //On regarde si on a l'user
            if(!haveClient(client)) return false;

            users.remove(client);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean haveClient(T client)
    {
        try
        {
            if(client == null) return false;

            for(T c : users)
            {
                if(c == null) continue;

                if(c == client) return true;
            }

            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean isEmpty()
    {
        try
        {
            return users.size() == 0;
        }
        catch(Exception e)
        {
            return true;
        }
    }
}
