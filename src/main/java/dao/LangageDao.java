package dao;

import model.Langage;

import javax.persistence.EntityManager;

/**
 * Created by Naeno on 24/11/2016.
 */
public class LangageDao extends BaseDao implements ILangage {

    public LangageDao(EntityManager em) {
        super(em);
    }

    @Override
    public Langage get(int idLangage) {
        try {
            if (idLangage < 1) {
                return null;
            }

            return em.find(Langage.class, idLangage);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
