package dao;

import model.PrioriteTicket;

import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public interface IPrioriteTicketDao
{
    PrioriteTicket findById(int idPrioriteTicket);
    List<PrioriteTicket> getAll();
}
