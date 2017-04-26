package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by pierremarsot on 14/10/2016.
 */
@Entity
@Table(name = "element_projet")
public class ElementProjet extends AElement implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToOne
    private Dossier parent;

    @ManyToOne
    private Projet projet;

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

    public Dossier getParent() {
        return parent;
    }

    public void setParent(Dossier parent) {
        this.parent = parent;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
