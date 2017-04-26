package dao;

import model.Tracker;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class TrackerDao extends BaseDao implements ITrackerDao
{
    public TrackerDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de trouver un tracker via son id
     * @param idTracker - id du tracker
     * @return
     */
    @Override
    public Tracker findById(int idTracker)
    {
        try
        {
            if (idTracker < 1) {
                return null;
            }

            return em.find(Tracker.class, idTracker);
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet de récupérer tous les trackers
     * @return - la liste des trackers disponible
     */
    @Override
    public List<Tracker> getAll()
    {
        try
        {
            return em.createQuery("SELECT t FROM Tracker  t", Tracker.class)
                    .getResultList();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return new ArrayList<Tracker>();
        }
    }
}
