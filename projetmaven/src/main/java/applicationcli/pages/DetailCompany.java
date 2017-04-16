package applicationcli.pages;

import applicationcli.Application;
import applicationcli.FormulaireCli;
import model.Company;

public class DetailCompany extends Page {

    private Company company;

    /**
     * Constructor.
     * @param app belonging to
     * @param company details showed
     */
    public DetailCompany(Application app, Company company) {
        super(app);
        this.company = company;
    }

    /**
     * Print commands.
     */
    @Override
    public void printPageInfos() {
        System.out.println("---- Detail Entreprise '" + company.getName() + "' ----");
        System.out.println("1 : Modifier le nom de l'entreprise");
        System.out.println("exit : revenir");
    }

    /**
     * Compute user input.
     * @param command user input
     */
    @Override
    public void computeCommand(String command) {

        if (command.equals("1")) {
            FormulaireCli.updateCompanyName(company);
        } else if (command.equals("exit")) {
            app.popPage();
        } else {
            System.out.println("Non reconnu");
        }

    }


}
