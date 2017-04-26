package viewModel;

/**
 * Created by pierremarsot on 19/10/2016.
 */
public class CreateElementProjet
{
    private String name;
    private String type;
    private String idDossier;
    private String dossierRoot;
    private boolean main;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(String idDossier) {
        this.idDossier = idDossier;
    }

    public String getDossierRoot() {
        return dossierRoot;
    }

    public void setDossierRoot(String dossierRoot) {
        this.dossierRoot = dossierRoot;
    }

    public boolean getMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
