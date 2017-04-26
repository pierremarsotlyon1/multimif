package model.socketEditionFichier;

import java.io.Serializable;

/**
 * Created by pierremarsot on 18/10/2016.
 */
public class ConfigEditionFileSocket implements Serializable
{
    private int positionX;
    private int positionY;
    private String text;
    private int idFichier;

    public ConfigEditionFileSocket(int x, int y, String text, int idFichier)
    {
        this.setPositionX(x);
        this.setPositionY(y);
        this.setText(text);
        this.setIdFichier(idFichier);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }
}
