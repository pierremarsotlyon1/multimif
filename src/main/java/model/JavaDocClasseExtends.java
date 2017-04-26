package model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by pierremarsot on 17/11/2016.
 */
@Entity
public class JavaDocClasseExtends extends JavaDocClasse implements Serializable
{
    public JavaDocClasseExtends()
    {
        super();
    }

    public JavaDocClasseExtends(String name)
    {
        super(name);
    }
}
