package dao;

import model.PrioriteTicket;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public class PrioriteTicketDao extends BaseDao implements IPrioriteTicketDao
{
    public PrioriteTicketDao(EntityManager em)
    {
        super(em);
    }

    /**
     * Permet de trouver une prioriteTicket via son id
     * @param idPrioriteTicket - id du prioriteTicket
     * @return - le prioriteTicket
     */
    @Override
    public PrioriteTicket findById(int idPrioriteTicket)
    {
        try
        {
            if (idPrioriteTicket < 1) {
                return null;
            }

            return em.find(PrioriteTicket.class, idPrioriteTicket);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les prioriteTicket disponibles
     * @return - liste des prioriteTicket disponibles
     */
    @Override
    public List<PrioriteTicket> getAll()
    {
        try
        {
            return em.createQuery("SELECT p FROM PrioriteTicket p", PrioriteTicket.class)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }
}
