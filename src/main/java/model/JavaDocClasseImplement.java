package model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by pierremarsot on 17/11/2016.
 */
@Entity
public class JavaDocClasseImplement extends JavaDocClasse implements Serializable
{
    public JavaDocClasseImplement()
    {
        super();
    }

    public JavaDocClasseImplement(String name)
    {
        super(name);
    }
}
