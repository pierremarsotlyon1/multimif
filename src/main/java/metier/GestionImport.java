package metier;

import model.Droit;
import model.Projet;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;
import viewModel.ViewModelDroitUserProjet;

import javax.persistence.EntityManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by pierremarsot on 15/11/2016.
 */
public class GestionImport extends GestionBase {
    private GestionProjet gestionProjet;
    private GestionGit gestionGit;
    private GestionEditionProjet gestionEditionProjet;
    private GestionDroitUserProjet gestionDroitUserProjet;

    public GestionImport(EntityManager em)
    {
        super(em);
        gestionProjet = new GestionProjet(em);
        gestionGit = new GestionGit();
        gestionEditionProjet = new GestionEditionProjet(em);
        gestionDroitUserProjet = new GestionDroitUserProjet(em);
    }

    /**
     * Permet d'importer un projet
     * @param projet - le données du projet à ajouter
     * @param file - le zip qu contient le projet à ajouter
     * @param idUser - id de l'user connecté
     * @return - le projet ajouté
     */
    public Projet importProjet(Projet projet, MultipartFile file, int idUser)
    {
        try
        {
            if(projet == null || file == null || file.isEmpty() || idUser < 1) return null;

            //On récup les bytes du fichier
            byte[] bytes = file.getBytes();
            if(bytes == null || bytes.length == 0) return null;

            //Création du projet en bdd
            projet = gestionProjet.addProjet(projet, idUser);
            if(projet == null) return null;

            //On récup le path git du repo du projet
            String pathProjet = gestionGit.getPathProjet(projet);
            if(pathProjet == null || pathProjet.isEmpty()) return null;

            //On récup le nom du zip
            String fileName = fileName = file.getOriginalFilename();

            //On sauvegarde le zip dans le repo git
            File fileZip = new File(pathProjet + "/" + fileName);
            BufferedOutputStream buffStream =
                    new BufferedOutputStream(new FileOutputStream(fileZip));
            buffStream.write(bytes);
            buffStream.close();

            //On décompresse le zip
            ZipUtil.unpack(fileZip, new File(pathProjet));

            //On supprime le zip
            if(!fileZip.delete()) return null;

            //On ajoute tous les fichiers dans l'index du repo
            if(gestionGit.addAllFilesRepo(projet)) return null;

            //On recup l'arborescence via git du projet
            List<String> arborescence = gestionGit.getArborescenceRepo(projet);

            //On ajoute en bdd l'arborescence du projet
            if(!gestionEditionProjet.replaceArborescence(projet.getId(), arborescence, idUser)) return null;

            //On récup tous les droits en bdd
            List<Droit> droits = gestionDroitUserProjet.getAllDroits();

            //On ajoute tous les droits à l'user pour le projet
            gestionDroitUserProjet.manageDroitUser(projet.getId(), idUser, new ViewModelDroitUserProjet(droits));

            return projet;
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
