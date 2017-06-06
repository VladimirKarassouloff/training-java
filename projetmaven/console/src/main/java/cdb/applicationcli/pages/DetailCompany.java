package cdb.applicationcli.pages;

import cdb.applicationcli.FormulaireCli;
import cdb.applicationcli.Application;
import cdb.dto.CompanyDTO;
import cdb.model.Company;

public class DetailCompany extends Page {

    private CompanyDTO company;

    /**
     * Constructor.
     * @param app belonging to
     * @param company details showed
     */
    public DetailCompany(Application app, CompanyDTO company) {
        super(app);
        this.company = company;
    }

    /**
     * Print commands.
     */
    @Override
    public void printPageInfos() {
        System.out.println("---- Detail Entreprise '" + company.getName() + "' ----");
        System.out.println("exit : revenir");
    }

    /**
     * Compute user input.
     * @param command user input
     */
    @Override
    public void computeCommand(String command) {

        if (command.equals("exit")) {
            app.popPage();
        } else {
            System.out.println("Non reconnu");
        }

    }


}
