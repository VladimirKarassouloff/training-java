package cdb.applicationcli.pages;

import cdb.applicationcli.Application;
import cdb.dto.CompanyDTO;
import cdb.dto.ComputerDTO;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ListCompaniesPageForm extends ListCompaniesPage {

    private ComputerDTO form;

    /**
     * Constructor.
     *
     * @param app  belonging to
     * @param comp being updating
     */
    public ListCompaniesPageForm(Application app, ComputerDTO comp) {
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
        List<CompanyDTO> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());

        if (filterId.size() > 0) {
            CompanyDTO companyDto = filterId.get(0);
            form.setCompanyId(companyDto.getId());
            form.setCompanyName(companyDto.getName());
            this.app.popPage();

        } else {

            CompanyDTO tmp = app.getClient().getCompanyWithId(id);

            if (tmp != null) {
                form.setCompanyId(tmp.getId());
                form.setCompanyName(tmp.getName());
                this.app.popPage();
            } else {
                System.out.println("Entreprise introuvable");
            }

        }
    }

}
