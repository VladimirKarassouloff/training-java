package cdb.applicationcli.pages;

import cdb.applicationcli.Application;
import cdb.dto.CompanyDTO;
import cdb.model.Company;
import cdb.model.RestResponsePage;
import cdb.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ListCompaniesPage extends Pageable<CompanyDTO> {



    /**
     * Constructor.
     *
     * @param app belonging to
     */
    public ListCompaniesPage(Application app) {
        super(app, 8);
    }

    @Override
    public void printHeader() {
        System.out.println("---- Liste des entreprises ----");
        System.out.println("{id} : voir le détail par id");
        System.out.println("exit : revenir");
        System.out.println();
    }

    @Override
    public void printLine(int trueLine, int i) {
        System.out.println((trueLine) + "\t" + list.get(i).getName() + " / Id : " + list.get(i).getId());

    }

    @Override
    public void otherCommands(String command) {

    }

    @Override
    protected void orderFetchNewDataForPage() {
        RestResponsePage<CompanyDTO> page = app.getClient().getCompanies(new PageRequest(currentPage, numberItemPage));
        this.countItemTotal = page.getTotalElements();
        this.list = page.getContent();
    }

    @Override
    public void selected(int id) {
        // On regarde si on a deja le computer dans la liste, et si non, on va le chercher en base des companies
        List<CompanyDTO> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        if (filterId.size() > 0) {
            this.app.pushPage(new DetailCompany(app, filterId.get(0)));
        } else {
            CompanyDTO tmp = app.getClient().getCompanyWithId(id);
            if (tmp != null) {
                this.app.pushPage(new DetailCompany(app, tmp));
            }
        }
    }

}
