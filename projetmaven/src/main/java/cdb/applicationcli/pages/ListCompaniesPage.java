package cdb.applicationcli.pages;

import cdb.applicationcli.Application;
import cdb.model.Company;
import cdb.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ListCompaniesPage extends Pageable<Company> {


    @Autowired
    public CompanyServiceImpl companyService;


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
    protected long orderFetchDataCountPageable() {
        return companyService.getCountCompany();
    }

    @Override
    public void otherCommands(String command) {

    }

    @Override
    protected void orderFetchNewDataForPage() {
        this.list = companyService.getPagedCompany(new PageRequest(currentPage, numberItemPage)).getContent();
    }

    @Override
    public void selected(int id) {
        // On regarde si on a deja le computer dans la liste, et si non, on va le chercher en base des companies
        List<Company> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        if (filterId.size() > 0) {
            this.app.pushPage(new DetailCompany(app, filterId.get(0)));
        } else {
            Company tmp = companyService.getCompany(id);
            if (tmp != null) {
                this.app.pushPage(new DetailCompany(app, tmp));
            }
        }
    }

}
