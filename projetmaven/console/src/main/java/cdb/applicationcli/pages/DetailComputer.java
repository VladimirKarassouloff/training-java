package cdb.applicationcli.pages;

import cdb.applicationcli.FormulaireCli;
import cdb.applicationcli.Application;
import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.mapper.MapperDate;
import cdb.model.Computer;

import java.util.Date;

public class DetailComputer extends Page {


    private ComputerDTO computer;
    private boolean isEditingCompanyId;

    /**
     * Constructor for DetailComputer.
     *
     * @param app      belonging to
     * @param computer details showed
     */
    public DetailComputer(Application app, ComputerDTO computer) {
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
        System.out.println("Marque : " + (this.computer.getCompanyName() != null ? this.computer.getCompanyName() : "Inconnue"));
        System.out.println("Date d'introduction dans le marché : " + computer.getIntroduced() );
        System.out.println("Date d'arret de commercialisation : " + computer.getDiscontinued() );
    }

    @Override
    public void computeCommand(String command) {

        if (command.equals("1")) { // Changement du nom de l ordianteur
            updateComputerName(computer);
        } else if (command.equals("2")) { // Changement de la date d'introduction sur le marché
            updateComputerIntroduced(computer);
        } else if (command.equals("3")) { // Changement de la date d'arret de commercialisation
            updateComputerDiscontinuedDate(computer);
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
            System.out.println("Succes de la modification");
        }
    }


    /**
     * Basic form to update the introduced date of a computer.
     *
     * @param computer to update
     */
    public void updateComputerIntroduced(ComputerDTO computer) {
        System.out.println("Entrez la nouvelle date");
        Date newDate = FormulaireCli.reclaimDateOrNullInput();
        computer.setIntroduced(MapperDate.formatDate(newDate));
        try {
            app.getClient().editComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer");
        }
    }

    /**
     * Basic form to update the discontinued date of a computer.
     *
     * @param computer to update
     */
    public void updateComputerDiscontinuedDate(ComputerDTO computer) {
        System.out.println("Entrez la nouvelle date");
        Date newDate = FormulaireCli.reclaimDateOrNullInput();
        computer.setDiscontinued(MapperDate.formatDate(newDate));
        try {
            app.getClient().editComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer : " + e.getMessage());
        }
    }


    /**
     * Basic form to update the name of a computer.
     *
     * @param computer to update
     */
    public void updateComputerName(ComputerDTO computer) {
        System.out.println("Entrez le nouveau nom");
        computer.setName(FormulaireCli.input.nextLine());
        try {
            app.getClient().editComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer");
        }
    }

}
