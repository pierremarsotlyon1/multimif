package dao;

import model.Dossier;

import javax.persistence.EntityManager;

public class DossierDao extends BaseDao implements IDossierDao {
    public DossierDao(EntityManager em) {
        super(em);
    }

    /**
     * Permet de récupérer un dossier dans un projet
     * @param idDossier - id du dossier
     * @param idProjet - id du projet
     * @return - le dossier
     */
    @Override
    public Dossier get(int idDossier, int idProjet) {
        try
        {
            if (idDossier < 1 || idProjet < 1) {
                return null;
            }

            return em.createQuery("SELECT d FROM Dossier d INNER JOIN d.projet p WHERE d.id = :idDossier AND p.id = :idProjet", Dossier.class)
                    .setParameter("idDossier", idDossier)
                    .setParameter("idProjet", idProjet)
                    .getSingleResult();
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * Permet d'ajouter un dossier en bdd
     * @param dossier - le dossier à ajouter
     * @return - le dossier ajouté
     */
    public Dossier addDossier(Dossier dossier) {
        try {
            if (dossier == null) {
                return null;
            }

            em.persist(dossier);

            return dossier;
        }
        catch(Exception e)
        {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
