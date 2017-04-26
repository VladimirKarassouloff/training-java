package applicationcli.pages;

import applicationcli.Application;
import applicationcli.FormulaireCli;
import mapper.MapperDate;
import model.Computer;
import services.ComputerServices;
import utils.Format;

public class DetailComputer extends Page {


    private Computer computer;
    private boolean isEditingCompanyId;

    /**
     * Constructor for DetailComputer.
     *
     * @param app      belonging to
     * @param computer details showed
     */
    public DetailComputer(Application app, Computer computer) {
        super(app);
        this.computer = computer;
        this.isEditingCompanyId = false;
    }

    @Override
    public void printPageInfos() {
        System.out.println("---- Detail ordinateur : " + computer.getName() + " ----");
        System.out.println("1 : changer le nom de l'ordinateur");
        System.out.println("2 : changer la date commercialisation (yyyy-MM-dd)");
        System.out.println("3 : changer la date d'arret (yyyy-MM-dd)");
        System.out.println("4 : changer la marque");
        System.out.println("exit : quitter la page");
        System.out.println();
        System.out.println("--Infos--");
        System.out.println("Marque : " + (this.computer.getCompany() != null ? this.computer.getCompany().getName() : "Inconnue"));
        System.out.println("Date d'introduction dans le marché : " + MapperDate.formatDate(computer.getIntroduced()));
        System.out.println("Date d'arret de commercialisation : " + MapperDate.formatDate(computer.getDiscontinued()));
    }

    @Override
    public void computeCommand(String command) {

        if (command.equals("1")) { // Changement du nom de l ordianteur
            FormulaireCli.updateComputerName(computer);
        } else if (command.equals("2")) { // Changement de la date d'introduction sur le marché
            FormulaireCli.updateComputerIntroduced(computer);
        } else if (command.equals("3")) { // Changement de la date d'arret de commercialisation
            FormulaireCli.updateComputerDiscontinuedDate(computer);
        } else if (command.equals("4")) { // Nouvelle entreprise
            isEditingCompanyId = true;
            this.app.pushPage(new ListCompaniesPageForm(app, computer));
        } else if (command.equals("exit")) { // Sortie de la page
            app.popPage();
        } else {
            System.out.println("Non reconnu");
        }
    }

    @Override
    public void onFirstGroundEvent() {
        if (isEditingCompanyId) {
            isEditingCompanyId = false;
            if (ComputerServices.updateComputer(computer)) {
                System.out.println("Succes de la modification");
            } else {
                System.out.println("Erreur durant la modification");
            }
        }
    }


}
