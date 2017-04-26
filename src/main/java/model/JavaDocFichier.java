package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 16/11/2016.
 */
@Entity
@Table(name = "javadoc_fichier")
public class JavaDocFichier implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String packageName;

    private String classeName;

    @OneToMany(mappedBy="javaDocFichier")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JavaDocImport> javaDocImportList;

    @OneToMany(mappedBy="javaDocFichier")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JavaDocMethode> javaDocMethodes;

    @OneToMany(mappedBy="javaDocFichier")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<JavaDocClasse> javaDocClasses;

    @OneToOne
    private Fichier fichier;

    public JavaDocFichier()
    {
        this.setJavaDocImportList(new ArrayList<JavaDocImport>());
        this.setJavaDocMethodes(new ArrayList<JavaDocMethode>());
        this.setJavaDocClasses(new ArrayList<JavaDocClasse>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<JavaDocImport> getJavaDocImportList() {
        return javaDocImportList;
    }

    public void setJavaDocImportList(List<JavaDocImport> javaDocImportList) {
        this.javaDocImportList = javaDocImportList;
    }

    public List<JavaDocMethode> getJavaDocMethodes() {
        return javaDocMethodes;
    }

    public void setJavaDocMethodes(List<JavaDocMethode> javaDocMethodes) {
        this.javaDocMethodes = javaDocMethodes;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClasseName() {
        return classeName;
    }

    public void setClasseName(String classeName) {
        this.classeName = classeName;
    }

    public List<JavaDocClasse> getJavaDocClasses() {
        return javaDocClasses;
    }

    public void setJavaDocClasses(List<JavaDocClasse> javaDocClasses) {
        this.javaDocClasses = javaDocClasses;
    }
}
