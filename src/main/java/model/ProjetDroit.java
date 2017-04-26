package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 21/10/2016.
 */
@Entity
@Table(name = "projet_droit")
public class ProjetDroit implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Projet projet;

    @ManyToMany
    @JoinTable(name = "droit_projet", joinColumns = {
            @JoinColumn(name = "id_projet", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "id_droit",
                    nullable = false, updatable = false) })
    private List<Droit> droits;

    public ProjetDroit()
    {
        droits = new ArrayList<Droit>();
    }

    public boolean addDroit(Droit droit)
    {
        try
        {
            if(droit == null) return false;

            droits.add(droit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean removeDroit(Droit droit)
    {
        try
        {
            if(droit == null) return false;

            droits.remove(droit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<Droit> getDroits() {
        return droits;
    }

    public void setDroits(List<Droit> droits) {
        this.droits = droits;
    }
}
