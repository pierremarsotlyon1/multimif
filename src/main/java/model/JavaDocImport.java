package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pierremarsot on 16/11/2016.
 */
@Entity
@Table(name = "javadoc_import")
public class JavaDocImport implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    private JavaDocFichier javaDocFichier;

    public JavaDocImport() {

    }

    public JavaDocImport(String name) {
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
