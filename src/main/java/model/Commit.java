package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pierremarsot on 14/11/2016.
 */
public class Commit implements Serializable
{
    private String author;
    private String id;
    private Date date;

    public Commit(String author, String id, Date date)
    {
        this.setAuthor(author);
        this.setId(id);
        this.setDate(date);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
