package applicationcli.pages;

import applicationcli.Application;

public class MainPage extends Page {

    /**
     * Constructor.
     * @param app belonging to
     */
    public MainPage(Application app) {
        super(app);
    }


    @Override
    public void printPageInfos() {
        System.out.println("----Menu principal----");
        System.out.println("1 : Montrer les entreprises");
        System.out.println("2 : Montrer les ordinateurs");
        System.out.println("exit : Quitter");
    }


    @Override
    public void computeCommand(String command) {

        if (command.equals("1")) {
            app.pushPage(new ListCompaniesPage(app));
        } else if (command.equals("2")) {
            app.pushPage(new ListComputerPage(app));
        } else if (command.equals("exit")) {
            app.popPage();
        } else {
            System.out.println("Non reconnu");
        }

    }

}
