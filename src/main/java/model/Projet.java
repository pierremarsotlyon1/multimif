package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 11/10/2016.
 */
@Entity
@Table(name = "projet")
public class Projet implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToOne
    private Langage langage;

    private String description;

    @ManyToMany(mappedBy = "projets", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToOne(mappedBy="projet")
    private Wiki wiki;

    @OneToMany(mappedBy="projet")
    private List<Ticket> tickets;

    @OneToMany(mappedBy="projet")
    private List<Branche> branches;

    @OneToMany(mappedBy="projet")
    private List<ElementProjet> elementProjets;

    @OneToMany(mappedBy="projet")
    private List<ProjetDroit> projetDroits;

    @Transient
    private int idLangage;

    public Projet()
    {
        setUsers(new ArrayList<User>());
        tickets = new ArrayList<Ticket>();
        setElementProjets(new ArrayList<ElementProjet>());
        setProjetDroits(new ArrayList<ProjetDroit>());
    }

    public boolean addProjetDroit(ProjetDroit projetDroit)
    {
        try
        {
            if(projetDroit == null) return false;

            projetDroits.add(projetDroit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean addUser(User user)
    {
        try
        {
            if(user == null) return false;

            users.add(user);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Wiki getWiki() {
        return wiki;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Branche> getBranches() {
        return branches;
    }

    public void setBranches(List<Branche> branches) {
        this.branches = branches;
    }

    public List<ElementProjet> getElementProjets() {
        return elementProjets;
    }

    public void setElementProjets(List<ElementProjet> elementProjets) {
        this.elementProjets = elementProjets;
    }

    public List<ProjetDroit> getProjetDroits() {
        return projetDroits;
    }

    public void setProjetDroits(List<ProjetDroit> projetDroits) {
        this.projetDroits = projetDroits;
    }

    public Langage getLangage() {
        return langage;
    }

    public void setLangage(Langage langage) {
        this.langage = langage;
    }

    public int getIdLangage() {
        return idLangage;
    }

    public void setIdLangage(int idLangage) {
        this.idLangage = idLangage;
    }
}
