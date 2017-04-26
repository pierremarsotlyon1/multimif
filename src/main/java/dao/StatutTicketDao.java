package dao;

import model.StatutTicket;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class StatutTicketDao extends BaseDao implements IStatutTicketDao
{
    public StatutTicketDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer un statusTicket via son id
     * @param idStatutTicket - id du statusTicket
     * @return
     */
    @Override
    public StatutTicket findById(int idStatutTicket)
    {
        try
        {
            if (idStatutTicket < 1) {
                return null;
            }

            return em.find(StatutTicket.class, idStatutTicket);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les statusTicket disponibles
     * @return - la liste des statusTicket disponible
     */
    @Override
    public List<StatutTicket> getAll()
    {
        try
        {
            return em.createQuery("SELECT s FROM StatutTicket s")
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<>();
        }
    }
}
