package viewModel;

import model.Droit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierremarsot on 21/10/2016.
 */
public class ViewModelDroitUserProjet implements Serializable{

    private List<Droit> droits;

    public ViewModelDroitUserProjet()
    {
        this.droits = new ArrayList<Droit>();
    }

    public ViewModelDroitUserProjet(List<Droit> droits)
    {
        this.droits = droits;
    }

    public List<Droit> getDroits() {
        return droits;
    }

    public void setDroits(List<Droit> droits) {
        this.droits = droits;
    }

    public void add(Droit droit) {
        this.droits.add(droit);
    }

    public void addRange(List<Droit> droits)
    {
        this.droits.addAll(droits);
    }
}
