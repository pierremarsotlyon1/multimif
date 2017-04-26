package dao;

import model.StatutTicket;

import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public interface IStatutTicketDao
{
    StatutTicket findById(int idStatutTIcket);

    List getAll();
}
