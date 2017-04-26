package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pierremarsot on 17/11/2016.
 */
@Entity
@Table(name = "javadoc_classe")
public class JavaDocClasse implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    private JavaDocFichier javaDocFichier;

    public JavaDocClasse()
    {

    }

    public JavaDocClasse(String name)
    {
        this();
        this.name = name;
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

    public JavaDocFichier getJavaDocFichier() {
        return javaDocFichier;
    }

    public void setJavaDocFichier(JavaDocFichier javaDocFichier) {
        this.javaDocFichier = javaDocFichier;
    }
}
