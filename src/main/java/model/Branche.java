package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by pierremarsot on 14/10/2016.
 */
@Entity
@Table(name = "branche")
public class Branche implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="branche")
    private List<User> users;

    @ManyToOne
    private Projet projet;
}
