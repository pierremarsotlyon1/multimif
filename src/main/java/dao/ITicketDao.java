package dao;

import model.Ticket;

import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public interface ITicketDao
{
    Ticket findById(int idTicket);
    List<Ticket> getAll(int idProjet);
    Ticket getTicket(int idProjet, int idTicket);
}
