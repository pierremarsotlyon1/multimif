package viewModel;

import model.Droit;
import model.Projet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pierremarsot on 16/11/2016.
 */
public class ViewModelListingProjet
{
    public Map<Projet, List<Droit>> projetListMap;

    public ViewModelListingProjet()
    {
        projetListMap = new HashMap<>();
    }
}
