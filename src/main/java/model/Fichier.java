package model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by pierremarsot on 14/10/2016.
 */
@Entity
public class Fichier extends ElementProjet implements Serializable {
    @NotNull
    private String extension;

    private boolean main;

    @OneToOne(mappedBy="fichier")
    private JavaDocFichier javaDocFichier;

    public Fichier() {

    }

    public Fichier(String name) {
        this();
        this.setName(name);
    }

    public boolean calculateExtension() {
        try {
            int i = getName().lastIndexOf('.');
            if (i > 0) {
                setExtension(getName().substring(i + 1));
                setName(getName().substring(0, i));
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCheminComplet() {
        Dossier temp = getParent();
        String response = "";
        while (temp != null) {
            String nomDossier = temp.getName();
            if (nomDossier == null || nomDossier.isEmpty()) {
                return null;
            }

            temp = temp.getParent();
            if (temp == null) {
                response = nomDossier + response;
            } else {
                response = "/" + nomDossier + response;
            }
        }
        if (response.isEmpty()) {
            response = getName();
        } else {
            response += "/" + getName();
        }
        return response;
    }

    public String getNomComplet() {
        return getName() + "." + getExtension();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public JavaDocFichier getJavaDocFichier() {
        return javaDocFichier;
    }

    public void setJavaDocFichier(JavaDocFichier javaDocFichier) {
        this.javaDocFichier = javaDocFichier;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
