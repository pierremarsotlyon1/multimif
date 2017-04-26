package model.socketEditionFichier;

import java.io.Serializable;

public class FileContent implements Serializable{
    private int idFichier;
    private String contentFichier;

    public FileContent(int idFichier, String contentFichier) {
        this.idFichier = idFichier;
        this.contentFichier = contentFichier;
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }

    public String getContentFichier() {
        return contentFichier;
    }

    public void setContentFichier(String contentFichier) {
        this.contentFichier = contentFichier;
    }

}
