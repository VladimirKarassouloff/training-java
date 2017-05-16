package cdb.applicationcli.pages;

import cdb.applicationcli.Application;

import java.util.List;

public abstract class Pageable<T> extends Page {

    protected int currentPage;
    protected int numberItemPage;
    protected int countItemTotal;

    protected List<T> list;

    /**
     * Constuctor.
     * @param app belongings to
     * @param numberItemPage number item (pagination)
     */
    public Pageable(Application app, int numberItemPage) {
        super(app);
        this.currentPage = 0;
        this.numberItemPage = numberItemPage;
        this.countItemTotal = orderFetchDataCountPageable();
        orderFetchNewDataForPage();
    }


    /**
     * Assert that current page is above 0 and not superior to getLastPage().
     * @return vrai si la page est correct
     */
    protected boolean checkPageIsCorrect() {
        // Verification que l'on se trouve a une page correcte
        if (currentPage > getLastPage()) {
            currentPage = getLastPage();
            return false;
        } else if (currentPage < 0) {
            currentPage = 0;
            return false;
        }
        return true;
    }


    @Override
    public final void printPageInfos() {
        this.checkPageIsCorrect();
        this.printHeader();
        for (int i = currentPage * numberItemPage; i < countItemTotal
                && i < currentPage * numberItemPage + numberItemPage; i++) {
            this.printLine(i + 1, i - (currentPage * numberItemPage));
        }
    }

    /**
     * Return the max page depending of the number of item displayed per page and total count of database count.
     * @return page max
     */
    protected int getLastPage() {
        double d = ((double) countItemTotal / (double) numberItemPage);
        if (d % 1 != 0) {
            return (int) d;
        } else {
            return (int) d - 1;
        }
    }


    /**
     * Should get the row count from the database.
     * @return nombre de lignes
     */
    protected abstract int orderFetchDataCountPageable();

    /**
     * Should get new data from database depending of the currentpage.
     */
    protected abstract void orderFetchNewDataForPage();

    /**
     * Print header.
     */
    public abstract void printHeader();

    /**
     * Display an element in the CLI.
     *
     * @param trueLine refer to the position of the element in the database
     * @param i        refer to the position of element to display in the list
     */
    public abstract void printLine(int trueLine, int i);

    @Override
    public final void computeCommand(String command) {

        if (command.equals("n")) {

            if (getLastPage() > currentPage) {
                this.currentPage++;
                orderFetchNewDataForPage();
            } else {
                System.out.println("Pas de page suivante");
            }

        } else if (command.equals("p")) {

            if (currentPage > 0) {
                this.currentPage--;
                orderFetchNewDataForPage();
            } else {
                System.out.println("Pas de page précédente");
            }

        } else if (command.equals("last")) {
            this.currentPage = getLastPage();
            orderFetchNewDataForPage();
        } else if (command.equals("first")) {
            this.currentPage = 0;
            orderFetchNewDataForPage();
        } else if (command.equals("exit")) {
            app.popPage();
        } else {
            // On test si l'utilisateur essaye d'acceder au detail de la liste
            try {
                selected(Integer.parseInt(command));
            } catch (Exception e) {
                otherCommands(command);
            }
        }

    }


    /**
     * Event triggered when user send a number in input.
     *
     * @param id input of the user
     */
    public abstract void selected(int id);


    /**
     * Implementation of command for children of Pageable class.
     *
     * @param command input from user
     */
    public abstract void otherCommands(String command);


}
