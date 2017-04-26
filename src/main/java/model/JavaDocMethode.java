package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by pierremarsot on 16/11/2016.
 */
@Entity
@Table(name = "javadoc_methode")
public class JavaDocMethode implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String declaration;

    @NotNull
    @Basic(fetch = LAZY)
    @Lob
    private byte[] contentJavaDoc;

    @OneToMany(mappedBy = "javaDocMethode")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JavaDocAttribut> javaDocAttribut;

    @ManyToOne
    private JavaDocFichier javaDocFichier;

    public JavaDocMethode() {
        javaDocAttribut = new ArrayList<>();
    }

    public JavaDocMethode(String name, String declaration, String contentJavaDoc) {
        this();
        this.name = name;
        this.declaration = declaration;
        setContentJavaDoc(contentJavaDoc);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getContentJavaDoc() {
        try {
            return new String(contentJavaDoc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public void setContentJavaDoc(String contentJavaDoc) {
        byte[] b = contentJavaDoc.getBytes();
        this.contentJavaDoc = b;
    }

    public JavaDocFichier getJavaDocFichier() {
        return javaDocFichier;
    }

    public void setJavaDocFichier(JavaDocFichier javaDocFichier) {
        this.javaDocFichier = javaDocFichier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JavaDocAttribut> getJavaDocAttribut() {
        return javaDocAttribut;
    }

    public void setJavaDocAttribut(List<JavaDocAttribut> javaDocAttribut) {
        this.javaDocAttribut = javaDocAttribut;
    }
}
