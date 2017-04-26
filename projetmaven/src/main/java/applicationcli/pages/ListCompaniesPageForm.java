package applicationcli.pages;

import applicationcli.Application;
import model.Company;
import model.Computer;
import services.CompanyServices;

import java.util.List;
import java.util.stream.Collectors;

public class ListCompaniesPageForm extends ListCompaniesPage {

    private Computer form;

    /**
     * Constructor.
     * @param app belonging to
     * @param comp being updating
     */
    public ListCompaniesPageForm(Application app, Computer comp) {
        super(app);
        this.form = comp;
    }

    @Override
    public void printHeader() {
        System.out.println("---- Chosir la nouvelle entreprise rattachée ----");
        System.out.println("{id} : Selectionner l'entreprise");
        System.out.println();
    }


    @Override
    public void selected(int id) {
        List<Company> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());

        if (filterId.size() > 0) {

            form.setCompany(filterId.get(0));
            this.app.popPage();

        } else {

            Company tmp = CompanyServices.getInstance().getCompany(id);

            if (tmp != null) {
                form.setCompany(tmp);
                this.app.popPage();
            } else {
                System.out.println("Entreprise introuvable");
            }

        }
    }

}
