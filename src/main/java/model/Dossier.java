package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dossier extends ElementProjet implements Serializable
{
    @OneToMany(mappedBy = "parent")
    private List<ElementProjet> elementProjets;

    public Dossier()
    {
        setElementProjets(new ArrayList<ElementProjet>());
    }

    public Dossier(String name)
    {
        this();
        this.setName(name);
    }

    public Dossier(int id, String name)
    {
        this(name);
        this.setId(id);
    }

    public String createArborescence()
    {
        try
        {
            if(elementProjets == null) return null;

            //On trie les dossiers et fichiers du dossier
            String chemin = getProjet().getName();
            if(chemin.isEmpty()) return "";

            String response = "<ul class='treeview'>";
                response += "<li><span href='#' data-root='1' data-id='"+getId()+"'>"+getName()+"</span><ul>";
                response += createArborescence(getElementProjets(), chemin);
            response += "</ul></li></ul>";
            return response;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    private String createArborescence(List<ElementProjet> elementProjets, String chemin)
    {
        try
        {
            if(elementProjets == null || elementProjets.isEmpty()) return "";

            String response = "";

            for(ElementProjet e : elementProjets)
            {
                if(e instanceof Dossier)
                {
                    Dossier dossier = (Dossier)e;
                    if(dossier == null) continue;
                    response += "<li><span href='#' data-id='"+dossier.getId()+"'>"+dossier.getName()+"</span><ul>";
                    response += createArborescence(dossier.getElementProjets(), chemin + "/" + dossier.getName());
                    response += "</ul></li>";
                }
                else if(e instanceof Fichier)
                {
                    Fichier fichier = (Fichier)e;
                    if(fichier == null) continue;
                    response += "<li>" +
                            "<span href='#' data-id='"+fichier.getId()+"' data-path='"+chemin + "/"
                            + fichier.getNomComplet() + "' class='fichier'>"
                            +fichier.getNomComplet()+
                            "</span> " +
                            "<i class='fa fa-trash-o deleteFileProjet text-danger' " +
                            " data-id='"+fichier.getId()+"' aria-hidden='true'></i>";

                    if(fichier.isMain())
                    {
                        response += "<i data-toggle='tooltip' data-placement='top' data-original-title='Permet de marquer" +
                                " le fichier comme non executable' class='fa fa-stop setFichierUnMain' data-id='"+fichier.getId()+"' aria-hidden='true'></i>";
                    }
                    else
                    {
                        response += "<i data-toggle='tooltip' data-placement='top' data-original-title='Permet de marquer" +
                                " le fichier comme executable' class='fa fa-play setFichierMain' " +
                                "data-id='"+fichier.getId()+"' aria-hidden='true'></i>";
                    }
                        response +=    "</li>";
                }
            }

            return response;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public List<ElementProjet> getElementProjets() {
        return elementProjets;
    }

    public void setElementProjets(List<ElementProjet> elementProjets) {
        this.elementProjets = elementProjets;
    }
}
