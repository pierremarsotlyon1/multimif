package metier;

import dao.DroitDao;
import dao.ProjetDao;
import dao.UserDao;
import model.Droit;
import model.Projet;
import model.ProjetDroit;
import model.User;
import viewModel.ViewModelDroitUserProjet;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class GestionDroitUserProjet extends GestionBase
{
    private UserDao userDao;
    private DroitDao droitDao;
    private ProjetDao projetDao;

    public GestionDroitUserProjet(EntityManager em) {
        super(em);
        userDao = new UserDao(em);
        droitDao = new DroitDao(em);
        projetDao = new ProjetDao(em);
    }

    /**
     * Permet de gérer les droits d'un user sur un projet
     * @param idProjet - id du projet
     * @param idUser - id de l'user auquel on manage les droits
     * @param viewModelDroitUserProjet - le model contenant les nouveaux droits
     * @return - succès de la modification des droits
     */
    public boolean manageDroitUser(int idProjet, int idUser, ViewModelDroitUserProjet viewModelDroitUserProjet)
    {
        try
        {
            if(idProjet < 1 || idUser < 1 || viewModelDroitUserProjet == null) return false;

            //On récup la liste des id des droits
            ArrayList<Integer> idDroits = new ArrayList<Integer>();
            for(Droit droit : viewModelDroitUserProjet.getDroits())
            {
                if(droit == null || droit.getId() < 1) continue;

                idDroits.add(droit.getId());

            }
            //On ajuste les implications des droits
            for (int  i=0;i<idDroits.size();i++) {
                int idDroit=idDroits.get(i);
                switch (idDroit){
                    //compilation implique lecture
                    case 1:
                        if(!idDroits.contains(3)) idDroits.add(3);
                        break;
                        //execution implique compilation
                    case 2:
                        if(!idDroits.contains(1)) idDroits.add(1);
                        break;
                    //ecriture implique lecture
                    case 4:
                        if(!idDroits.contains(3))idDroits.add(3);
                        break;
                    //manager implique tout sauf administrateur
                    case 5:
                        if(!idDroits.contains(1))idDroits.add(1);
                        if(!idDroits.contains(2))idDroits.add(2);
                        if(!idDroits.contains(3))idDroits.add(3);
                        if(!idDroits.contains(4))idDroits.add(4);
                        if(!idDroits.contains(7))idDroits.add(7);
                        break;
                    //manager implique tout
                    case 6:
                        if(!idDroits.contains(1))idDroits.add(1);
                        if(!idDroits.contains(2))idDroits.add(2);
                        if(!idDroits.contains(3))idDroits.add(3);
                        if(!idDroits.contains(4))idDroits.add(4);
                        if(!idDroits.contains(5))idDroits.add(5);
                        if(!idDroits.contains(7))idDroits.add(7);
                        break;
                    //exporter un projet implique lecture
                    case 7:
                        if(!idDroits.contains(3))idDroits.add(3);
                        break;
                }
            }
            //On récup le projet
            Projet projet = projetDao.findByIdProjet(idProjet);
            if(projet == null) return false;

            //On récup l'user
            User user = userDao.findById(idUser);
            if(user == null) return false;

            //On récup les droits du l'user du projet
            ProjetDroit projetDroit = null;
            for(ProjetDroit p : user.getProjetDroits())
            {
                if(p.getUser().equals(user) && p.getProjet().equals(projet))
                {
                    projetDroit = p;
                    break;
                }
            }

            //Si aucun droits, alors c'est la première fois qu'on manage ses droits, on instancie un nouvel objet
            if(projetDroit == null)
            {
                projetDroit = new ProjetDroit();
                projetDroit.setProjet(projet);
                projetDroit.setUser(user);
                
                user.addProjetDroit(projetDroit);
                projet.addProjetDroit(projetDroit);
            }

            //On vide la liste des droits de l'user
            List<Droit> temp = projetDroit.getDroits();
            projetDroit.setDroits(new ArrayList<Droit>());

            for(Droit d : temp)
            {
                d.removeProjetDroit(projetDroit);
            }

            //On ajoute les nouveaux droits
            for(int id : idDroits)
            {
                Droit droitTemp = droitDao.get(id);
                if(droitTemp == null) continue;

                projetDroit.addDroit(droitTemp);
                droitTemp.addProjetDroit(projetDroit);
            }

            em.persist(projetDroit);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Permet de récupérer les droits de l'user sur un projet
     * @param idUser - id de l'user auquel on veut récupérer les droits
     * @param idProjet - id du projet
     * @return - la liste des droits de l"user
     */
    public List<Droit> getDroitsUser(int idUser, int idProjet)
    {
        try
        {
            return droitDao.getDroitsUser(idUser, idProjet);
        }
        catch(Exception e)
        {
            return new ArrayList<>();
        }
    }

    /**
     * Permet de récupérer la liste des droits de la bdd
     * @return
     */
    public List<Droit> getAllDroits()
    {
        try
        {
            return droitDao.getAll();
        }
        catch(Exception e)
        {
            return new ArrayList<>();
        }
    }
}
