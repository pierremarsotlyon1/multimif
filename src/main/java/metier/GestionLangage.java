package metier;

import dao.LangageDao;
import model.Langage;

import javax.persistence.EntityManager;

/**
 * Created by Naeno on 24/11/2016.
 */
public class GestionLangage extends GestionBase{
    private LangageDao langageDao;

    public GestionLangage(EntityManager em) {
        super(em);
        langageDao = new LangageDao(em);
    }

    public Langage getLangage(int idLangage)
    {
        try
        {
            return langageDao.get(idLangage);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
