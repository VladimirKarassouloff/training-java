package cdb.applicationcli.pages;

import cdb.applicationcli.Application;
import cdb.applicationcli.FormulaireCli;
import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.model.Computer;
import cdb.model.RestResponsePage;
import cdb.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ListComputerPage extends Pageable<ComputerDTO> {


    private boolean isCreatingComputer = false;
    private ComputerDTO computerCreation = null;

    /**
     * Constructor.
     *
     * @param app belonging to
     */
    public ListComputerPage(Application app) {
        super(app, 8);
    }

    @Override
    public void printHeader() {
        System.out.println("---- Liste des ordinateurs ----");
        System.out.println("{id} : voir le détail par id");
        System.out.println("c : creer un ordinateur");
        System.out.println("d : supprimer un ordinateur");
        System.out.println("exit : revenir");

    }

    /**
     * Print detail for the paged result at index i, refering to the line 'trueLine' in DB.
     *
     * @param trueLine refer to the position of the element in the database
     * @param i        refer to the position of element to display in the list
     */
    @Override
    public void printLine(int trueLine, int i) {
        System.out.println(trueLine + "\tName : " + list.get(i).getName() + " // Id : " + list.get(i).getId());
    }

    /**
     * User asked for deletion.
     */
    public void handleDeletion() {
        System.out.println("Entrez le numero l'id de l'ordinateur a supprimer");
        try {
            long idDelete = Long.parseLong(input.nextLine());
            ComputerDTO comp = app.getClient().getComputer(idDelete);
            if (comp == null) {
                throw new RuntimeException("Id invalide");
            }
            app.getClient().deleteComputer(comp.getId());
            countItemTotal--;
            if (!checkPageIsCorrect()) {
                orderFetchNewDataForPage();
            }
            System.out.println("Suppression reussie");
        } catch (RuntimeException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

    /**
     * User asked for creation.
     */
    public void handleCreation() {
        isCreatingComputer = true;
        this.computerCreation = FormulaireCli.precreateComputer();
        this.app.pushPage(new ListCompaniesPageForm(app, computerCreation));
    }

    @Override
    public void otherCommands(String command) {

        if (command.equals("c")) {
            handleCreation();
        } else if (command.equals("d")) {
            handleDeletion();
        } else if (command.equals("exit")) {
            app.popPage();
        } else {
            System.out.println("Non reconnu");
        }
    }

    @Override
    protected void orderFetchNewDataForPage() {
        RestResponsePage<ComputerDTO> page = app.getClient().getComputers(new PageRequest(currentPage, numberItemPage));
        this.list = page.getContent();
        this.countItemTotal = page.getTotalElements();
    }

    @Override
    public void selected(int id) {

        // On regarde si on a deja le computer dans la liste, et si non, on va le chercher en base des computer
        List<ComputerDTO> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        if (filterId.size() > 0) {
            this.app.pushPage(new DetailComputer(app, filterId.get(0)));
        } else {
            ComputerDTO idComputer = app.getClient().getComputer(id);
            if (idComputer != null) {
                this.app.pushPage(new DetailComputer(app,  app.getClient().getComputer(id)));
            } else {
                System.out.println("Cet ordinateur n'existe pas");
            }
        }

    }

    @Override
    public void onFirstGroundEvent() {
        if (isCreatingComputer) {

            try {
                app.getClient().addComputer(this.computerCreation);
                isCreatingComputer = false;
                this.countItemTotal++;
                this.orderFetchNewDataForPage();
                computerCreation = null;
            } catch (FormException e) {
                System.out.println("Error while adding computer");
            }

        }
    }


}
