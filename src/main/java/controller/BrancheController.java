package controller;

import metier.GestionBranche;
import org.springframework.stereotype.Controller;

/**
 * Created by pierremarsot on 14/10/2016.
 */
@Controller
public class BrancheController extends GestionController
{
    private GestionBranche gestionBranche;

    public BrancheController()
    {

    }
}
