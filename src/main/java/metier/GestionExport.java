package metier;

import dao.ProjetDao;
import model.Projet;
import org.zeroturnaround.zip.ZipUtil;

import javax.persistence.EntityManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by pierremarsot on 15/11/2016.
 */
public class GestionExport extends GestionBase
{
    private ProjetDao projetDao;
    private GestionGit gestionGit;

    public GestionExport(EntityManager em) {
        super(em);
        projetDao = new ProjetDao(em);
        gestionGit = new GestionGit();
    }

    /**
     * Permet de créer un zip du projet
     * @param idProjet - id du projet
     * @return le zip en bytes
     */
    public byte[] exportProjet(int idProjet)
    {
        try
        {
            if(idProjet < 1) return null;

            //On récup le projet
            Projet projet = projetDao.findByIdProjet(idProjet);
            if(projet == null) return null;

            //On récup le path du projet
            String pathProjet = gestionGit.getPathProjet(projet);
            if(pathProjet == null || pathProjet.isEmpty()) return null;

            //Création de l'objet file pour le zip
            File zip = new File(gestionGit.getPathRootGit() + "projetZip.zip");

            //Si on a déjà un zip, on le supprime
            if(zip.exists())
            {
                if(!zip.delete()) return null;
            }

            //Création du zip
            ZipUtil.pack(new File(pathProjet), zip);

            //Convert du zip en bytes
            byte[] bytes = Files.readAllBytes(Paths.get(zip.getAbsolutePath()));

            //On supprime le zip
            zip.delete();

            //On retourne le path du zip
            return bytes;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
