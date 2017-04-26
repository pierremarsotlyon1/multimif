package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pierremarsot on 17/11/2016.
 */
@Entity
@Table(name = "javadoc_attribut")
public class JavaDocAttribut implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;

    private String type;

    @ManyToOne
    private JavaDocMethode javaDocMethode;

    public JavaDocAttribut()
    {

    }

    public JavaDocAttribut(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "\"id\" : " + id + ", \"name\" : \"" + name + "\", \"type\" : \"" + type + "'";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JavaDocMethode getJavaDocMethode() {
        return javaDocMethode;
    }

    public void setJavaDocMethode(JavaDocMethode javaDocMethode) {
        this.javaDocMethode = javaDocMethode;
    }
}
