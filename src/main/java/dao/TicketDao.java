package dao;

import model.Ticket;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TicketDao extends BaseDao implements ITicketDao
{
    public TicketDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de trouver un ticket via son id
     * @param idTicket - id du ticket
     * @return - le ticket
     */
    @Override
    public Ticket findById(int idTicket)
    {
        try
        {
            if (idTicket < 1) {
                return null;
            }

            return em.find(Ticket.class, idTicket);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les tickets d'un projet
     * @param idProjet - id du projet
     * @return - la liste des tickets du projet
     */
    @Override
    public List<Ticket> getAll(int idProjet)
    {
        try
        {
            if (idProjet < 1) {
                return new ArrayList<>();
            }

            return em.createQuery("SELECT t FROM Ticket t INNER JOIN t.projet p WHERE p.id = :idProjet", Ticket.class)
                    .setParameter("idProjet", idProjet)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer un ticket d'un projet
     * @param idProjet - id du projet
     * @param idTicket - id du ticket
     * @return - le ticket
     */
    @Override
    public Ticket getTicket(int idProjet, int idTicket)
    {
        try
        {
            if (idProjet < 1 || idTicket < 1) {
                return null;
            }

            return em.createQuery("SELECT t FROM Ticket t INNER JOIN t.projet p INNER JOIN t.user u " +
                    "INNER JOIN t.statutTicket st INNER JOIN t.prioriteTicket prio INNER JOIN t.tracker tracker" +
                    " WHERE p.id = :idProjet AND t.id = :idTicket ", Ticket.class)
                    .setParameter("idProjet", idProjet)
                    .setParameter("idTicket", idTicket)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
