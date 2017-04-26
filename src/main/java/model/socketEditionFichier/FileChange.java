package model.socketEditionFichier;

import java.io.Serializable;

/**
 * Created by Naeno on 21/10/2016.
 */
public class FileChange implements Serializable{
    private int idFichier,fromLine,fromCh,toLine,toCh;
    private String[] text,removed;
    private String json;

    public FileChange() {
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }

    public int getFromLine() {
        return fromLine;
    }

    public void setFromLine(int fromLine) {
        this.fromLine = fromLine;
    }

    public int getFromCh() {
        return fromCh;
    }

    public void setFromCh(int fromCh) {
        this.fromCh = fromCh;
    }

    public int getToLine() {
        return toLine;
    }

    public void setToLine(int toLine) {
        this.toLine = toLine;
    }

    public int getToCh() {
        return toCh;
    }

    public void setToCh(int toCh) {
        this.toCh = toCh;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String[] getRemoved() {
        return removed;
    }

    public void setRemoved(String[] removed) {
        this.removed = removed;
    }
}
