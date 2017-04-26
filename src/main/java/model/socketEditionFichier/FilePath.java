package model.socketEditionFichier;

/**
 * Created by Naeno on 20/10/2016.
 */
public class FilePath {

    private int idFichier;

    private int oldId;
    private String pathFichier;

    public FilePath(int idFichier, String pathFichier,int oldId) {
        this.idFichier = idFichier;
        this.pathFichier = pathFichier;
        this.oldId = oldId;
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }

    public String getPathFichier() {
        return pathFichier;
    }

    public void setPathFichier(String pathFichier) {
        this.pathFichier = pathFichier;
    }


    public int getOldId() {
        return oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

}
