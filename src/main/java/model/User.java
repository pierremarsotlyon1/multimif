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
@Table(name = "user")
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String image;

    private boolean activate;

    private boolean googleAccount;

    @ManyToOne
    private Branche branche;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_projet", joinColumns = {
            @JoinColumn(name = "id_user", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "id_projet",
                    nullable = false, updatable = false) })
    private List<Projet> projets;

    @OneToMany(mappedBy="user")
    private List<ProjetDroit> projetDroits;

    @Transient
    private String confirmPassword;

    @OneToMany(mappedBy="user")
    private List<Ticket> tickets;

    public User()
    {
        setProjets(new ArrayList<Projet>());
        setTickets(new ArrayList<Ticket>());
        setProjetDroits(new ArrayList<ProjetDroit>());
    }

    public User(String email, String password)
    {
        this();
        this.setEmail(email);
        this.setPassword(password);
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

    /**
     * Permet de savoir si le mot de passe et le confirm mot de passe sont identiques
     * @return
     */
    public boolean samePassword()
    {
        try
        {
            if(getPassword() == null || getPassword().isEmpty() || getConfirmPassword() == null || getConfirmPassword().isEmpty())
                return false;

            return getPassword().equals(getConfirmPassword());
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean addProjet(Projet projet)
    {
        try
        {
            if(projet == null) return false;

            getProjets().add(projet);

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public List<ProjetDroit> getProjetDroits() {
        return projetDroits;
    }

    public void setProjetDroits(List<ProjetDroit> projetDroits) {
        this.projetDroits = projetDroits;
    }

    public boolean getActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public boolean isGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(boolean googleAccount) {
        this.googleAccount = googleAccount;
    }
}
