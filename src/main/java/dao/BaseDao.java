package dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class BaseDao
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
    protected EntityManager em;

    public BaseDao(EntityManager em)
    {
        this.em = em;
    }

    public boolean beginTransaction()
    {
        try
        {
            if(em == null || em.getTransaction().isActive()) return false;

            em.getTransaction().begin();
            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean commit()
    {
        try
        {
            if(em == null || !em.getTransaction().isActive()) return false;

            em.getTransaction().commit();
            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean rollback()
    {
        try
        {
            if(em == null || !em.getTransaction().isActive()) return false;

            em.getTransaction().rollback();
            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public boolean close()
    {
        try
        {
            if(em == null) return false;

            em.close();
            return true;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
