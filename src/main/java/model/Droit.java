package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "droit")
public class Droit implements Serializable,Comparable<Droit>
{
    @Transient
    public static final int Compilation = 1;
    @Transient
    public static final int Execution = 2;
    @Transient
    public static final int Lecture = 3;
    @Transient
    public static final int Ecriture = 4;
    @Transient
    public static final int Manager = 5;
    @Transient
    public static final int Administrateur = 6;
    @Transient
    public static final int Export = 7;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "droits", fetch = FetchType.LAZY)
    private List<ProjetDroit> projetDroits;

    @Transient
    private boolean checked;

    public boolean addProjetDroit(ProjetDroit projetDroit)
    {
        try
        {
            if (projetDroit == null) {
                return false;
            }

            getProjetDroits().add(projetDroit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean removeProjetDroit(ProjetDroit projetDroit)
    {
        try
        {
            if (projetDroit == null) {
                return false;
            }

            getProjetDroits().remove(projetDroit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public Droit() {

    }

    public Droit(int id)
    {
        this();
        this.id = id;
    }

    public Droit(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Droit)) {
            return false;
        }
        return this.id == ((Droit)obj).getId();
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<ProjetDroit> getProjetDroits() {
        return projetDroits;
    }

    public void setProjetDroits(List<ProjetDroit> projetDroits) {
        this.projetDroits = projetDroits;
    }

    @Override
    public int compareTo(Droit o) {
        return (this.getId() < o.getId()) ? -1 : ((this.getId() == o.getId()) ? 0 : 1);
    }
}
