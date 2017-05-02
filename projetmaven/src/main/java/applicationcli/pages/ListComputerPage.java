package applicationcli.pages;

import applicationcli.Application;
import applicationcli.FormulaireCli;
import exception.FormException;
import model.Computer;
import persistence.filter.FilterSelectComputer;
import services.ComputerServices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListComputerPage extends Pageable<Computer> {

    //protected List<Computer> list;

    private boolean isCreatingComputer = false;
    private Computer computerCreation = null;

    /**
     * Constructor.
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
            int idDelete = Integer.parseInt(input.nextLine());
            Computer comp = ComputerServices.getInstance().getComputer(idDelete);
            if (comp == null) {
                throw new Exception("Id invalide");
            } else if (ComputerServices.getInstance().deleteComputer(Arrays.asList(comp.getId()))) {
                countItemTotal--;
                if (!checkPageIsCorrect()) {
                    orderFetchNewDataForPage();
                }

                System.out.println("Suppression reussie");
            } else {
                throw new Exception("impossible de supprimer cet element");
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * User asked for creation.
     */
    public void handleCreation() {

        try {

            isCreatingComputer = true;
            this.computerCreation = FormulaireCli.precreateComputer();
            this.app.pushPage(new ListCompaniesPageForm(app, computerCreation));

        } catch (Exception e) {
            // System.out.println("Erreur lors de la creation d'un nouvel
            // ordinateur");
        }
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
        this.list = ComputerServices.getInstance().getPagedComputer(new FilterSelectComputer.Builder()
                .withPage(currentPage)
                .withLengthPage(numberItemPage)
                .build());
    }

    @Override
    protected int orderFetchDataCountPageable() {
        return ComputerServices.getInstance().getCountComputer();
    }

    @Override
    public void selected(int id) {

        // On regarde si on a deja le computer dans la liste, et si non, on va le chercher en base des computer
        List<Computer> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
        if (filterId.size() > 0) {
            this.app.pushPage(new DetailComputer(app, filterId.get(0)));
        } else {
            Computer idComputer = ComputerServices.getInstance().getComputer(id);
            if (idComputer != null) {
                this.app.pushPage(new DetailComputer(app, ComputerServices.getInstance().getComputer(id)));
            } else {
                System.out.println("Cet ordinateur n'existe pas");
            }
        }

    }

    @Override
    public void onFirstGroundEvent() {
        if (isCreatingComputer) {

            try {
                ComputerServices.getInstance().addComputer(this.computerCreation);
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
