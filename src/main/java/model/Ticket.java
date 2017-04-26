package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by pierremarsot on 13/10/2016.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String sujet;

    @NotNull
    private String description;

    private String tempsEstime;

    private String realisation;

    @ManyToOne
    private Projet projet;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tracker tracker;

    @ManyToOne
    private StatutTicket statutTicket;

    @ManyToOne
    private PrioriteTicket prioriteTicket;

    @Transient
    private String idTracker;

    @Transient
    private String idStatutTicket;

    @Transient
    private String idPrioriteTicket;

    @Transient
    private String idUserAssigne;

    public Ticket()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTempsEstime() {
        return tempsEstime;
    }

    public void setTempsEstime(String tempsEstime) {
        this.tempsEstime = tempsEstime;
    }

    public String getRealisation() {
        return realisation;
    }

    public void setRealisation(String realisation) {
        this.realisation = realisation;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public StatutTicket getStatutTicket() {
        return statutTicket;
    }

    public void setStatutTicket(StatutTicket statutTicket) {
        this.statutTicket = statutTicket;
    }

    public PrioriteTicket getPrioriteTicket() {
        return prioriteTicket;
    }

    public void setPrioriteTicket(PrioriteTicket prioriteTicket) {
        this.prioriteTicket = prioriteTicket;
    }

    public String getIdTracker() {
        return idTracker;
    }

    public void setIdTracker(String idTracker) {
        this.idTracker = idTracker;
    }

    public String getIdStatutTicket() {
        return idStatutTicket;
    }

    public void setIdStatutTicket(String idStatutTicket) {
        this.idStatutTicket = idStatutTicket;
    }

    public String getIdPrioriteTicket() {
        return idPrioriteTicket;
    }

    public void setIdPrioriteTicket(String idPrioriteTicket) {
        this.idPrioriteTicket = idPrioriteTicket;
    }

    public String getIdUserAssigne() {
        return idUserAssigne;
    }

    public void setIdUserAssigne(String idUserAssigne) {
        this.idUserAssigne = idUserAssigne;
    }
}
