package metier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class GestionBase
{
    protected EntityManager em;
    protected EntityTransaction transaction;

    public GestionBase(EntityManager em)
    {
        this.em = em;
        transaction = em.getTransaction();
    }

    /**
     * Permet de commencer une transaction en bdd
     * @return - succès du début de la transaction
     */
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
            return false;
        }
    }

    /**
     * Permet de faire un commit sur la bdd
     * @return - succès du commit
     */
    public boolean commit()
    {
        try
        {
            if(em == null) return false;

            em.getTransaction().commit();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de faire un rollback sur la connexion de la bdd
     * @return - succès du rollback
     */
    public boolean rollback()
    {
        try
        {
            if(em == null) return false;

            em.getTransaction().rollback();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de fermer la connexion à la bdd
     * @return - succès de la fermeture
     */
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
            return false;
        }
    }
}
