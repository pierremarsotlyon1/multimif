package metier;

import dao.*;
import model.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionTicket extends GestionBase
{
    private TrackerDao trackerDao;
    private PrioriteTicketDao prioriteTicketDao;
    private StatutTicketDao statutTicketDao;
    private TicketDao ticketDao;
    private ProjetDao projetDao;
    private UserDao userDao;

    public GestionTicket(EntityManager em)
    {
        super(em);
        trackerDao = new TrackerDao(em);
        prioriteTicketDao = new PrioriteTicketDao(em);
        statutTicketDao = new StatutTicketDao(em);
        ticketDao = new TicketDao(em);
        projetDao = new ProjetDao(em);
        userDao = new UserDao(em);
    }

    /**
     * Permet de récupérer tous les tickets d'un projet
     * @param idProjet - id du projet
     * @return - liste de tickets du projet
     */
    public List<Ticket> getAllTickets(int idProjet)
    {
        try
        {
            return (idProjet < 1) ? new ArrayList<Ticket>() : ticketDao.getAll(idProjet);
        }
        catch(Exception e)
        {
            return new ArrayList<Ticket>();
        }
    }

    /**
     * Permet de récupérer tous les trackers possibles pour les tickets
     * @return - liste des trackers
     */
    public List<Tracker> getAlltracker()
    {
        try
        {
            return trackerDao.getAll();
        }
        catch(Exception e)
        {
            return new ArrayList<Tracker>();
        }
    }

    /**
     * Permet de récupérer toutes les propriétés de tickets possibles
     * @return - liste des propriétés tickets
     */
    public List<PrioriteTicket> getAllPrioriteTickets()
    {
        try
        {
            return prioriteTicketDao.getAll();
        }
        catch(Exception e)
        {
            return new ArrayList<PrioriteTicket>();
        }
    }

    /**
     * Permet de récupérer tous les status possibles pour un ticket
     * @return - liste des status
     */
    public List<StatutTicket> getAllStatutTickets()
    {
        try
        {
            return statutTicketDao.getAll();
        }
        catch(Exception e)
        {
            return new ArrayList<StatutTicket>();
        }
    }

    /**
     * Permet d'ajouter un ticket
     * @param ticket - le ticket à ajouter
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @return - Le ticket ajouté
     */
    public Ticket addTicket(Ticket ticket, int idProjet, int idUser)
    {
        try
        {
            if(ticket == null || idUser < 1 || idProjet < 1 || ticket.getSujet().isEmpty()) return null;

            //On récup le projet
            Projet projet = projetDao.findById(idProjet, idUser);
            if(projet == null) return null;

            //On récup le tracker
            Tracker tracker = trackerDao.findById(Integer.parseInt(ticket.getIdTracker()));
            if(tracker == null) return null;

            //On récup le statut ticket
            StatutTicket statutTicket = statutTicketDao.findById(Integer.parseInt(ticket.getIdStatutTicket()));
            if(statutTicket == null) return null;

            //On récup la priorité
            PrioriteTicket prioriteTicket = prioriteTicketDao.findById(Integer.parseInt(ticket.getIdPrioriteTicket()));
            if(prioriteTicket == null) return null;

            //On récup l'user auquel le ticket a été assigné
            User user = userDao.findById(Integer.parseInt(ticket.getIdUserAssigne()));
            if(user == null) return null;

            //On ajoute le ticket a l'user
            user.getTickets().add(ticket);

            //On ajoute l'user au ticket
            ticket.setUser(user);

            //On ajoute le ticket au projet
            projet.getTickets().add(ticket);

            //On ajoute le projet au ticket
            ticket.setProjet(projet);

            //On ajoute le statut au ticket
            ticket.setStatutTicket(statutTicket);

            //On ajoute la priorite au ticket
            ticket.setPrioriteTicket(prioriteTicket);

            //On ajoute le tracker au ticket
            ticket.setTracker(tracker);

            //On persist le ticket
            em.persist(ticket);

            return ticket;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de modifier un ticket
     * @param ticket - Les nouvelles propriétés du ticket
     * @param idProjet - id du projet
     * @param idUser - id de l'user connecté
     * @param idTicket - id du ticket
     * @return - Ticket modifié
     */
    public Ticket updateTicket(Ticket ticket, int idProjet, int idUser, int idTicket)
    {
        try
        {
            if(ticket == null || idUser < 1 || idProjet < 1 || idTicket < 1) return null;

            //On récup le ticket non mis à jour
            Ticket ticketBdd = ticketDao.findById(idTicket);
            if(ticketBdd == null) return null;

            //On update le sujet, la description, le temps et la réalisation
            ticketBdd.setSujet(ticket.getSujet());
            ticketBdd.setDescription(ticket.getDescription());
            ticketBdd.setRealisation(ticket.getRealisation());
            ticketBdd.setTempsEstime(ticket.getTempsEstime());

            //On regarde si le statut à changé
            StatutTicket oldStatut = ticketBdd.getStatutTicket();
            if(oldStatut == null) return null;

            if(!String.valueOf(oldStatut.getId()).equals(ticket.getIdStatutTicket()))
            {
                //On récup le nouveau statut
                StatutTicket newStatut = statutTicketDao.findById(Integer.parseInt(ticket.getIdStatutTicket()));
                if(newStatut != null)
                {
                    ticketBdd.setStatutTicket(newStatut);
                }
            }

            //On regarde si la priorité à changéé
            PrioriteTicket oldPrioriteTicket = ticketBdd.getPrioriteTicket();
            if(oldPrioriteTicket == null) return null;

            if(!String.valueOf(oldPrioriteTicket.getId()).equals(ticket.getIdPrioriteTicket()))
            {
                //On récup le nouveau statut
                PrioriteTicket newPriorite = prioriteTicketDao.findById(Integer.parseInt(ticket.getIdPrioriteTicket()));
                if(newPriorite != null)
                {
                    ticketBdd.setPrioriteTicket(newPriorite);
                }
            }

            //On regarde si le tracker à changé
            Tracker oldTracker = ticketBdd.getTracker();
            if(oldTracker == null) return null;

            if(!String.valueOf(oldTracker.getId()).equals(ticket.getIdTracker()))
            {
                //On récup le nouveau statut
                Tracker newTracker = trackerDao.findById(Integer.parseInt(ticket.getIdTracker()));
                if(newTracker != null)
                {
                    ticketBdd.setTracker(newTracker);
                }
            }

            //On regarde si l'user auquel le ticket a été assigné à changé
            User oldUser = ticketBdd.getUser();
            if(oldUser == null) return null;

            if(!String.valueOf(oldUser.getId()).equals(ticket.getIdUserAssigne()))
            {
                //On récup le nouveau statut
                User newUser = userDao.findById(Integer.parseInt(ticket.getIdUserAssigne()));
                if(newUser != null)
                {
                    ticketBdd.setUser(newUser);
                }
            }

            em.persist(ticketBdd);

            return ticketBdd;

        }
        catch(Exception e)
        {
            transaction.rollback();
            return null;
        }
    }

    /**
     * Permet de récupérer un ticket
     * @param idProjet - id du projet
     * @param idTicket - id du ticket
     * @return
     */
    public Ticket getTicket(int idProjet, int idTicket)
    {
        try
        {
            return ticketDao.getTicket(idProjet, idTicket);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Permet de supprimer un ticket
     * @param idProjet - id du projet
     * @param idTicket - id du ticket
     * @return - ticket supprimé
     */
    public boolean deleteTicket(int idProjet, int idTicket)
    {
        try
        {
            if(idProjet < 1 || idTicket < 1) return false;

            //On récup le ticket
            Ticket ticket = getTicket(idProjet, idTicket);
            if(ticket == null) return false;

            //On supprime le ticket
            em.remove(ticket);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
