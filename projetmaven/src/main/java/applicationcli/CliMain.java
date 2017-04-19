package applicationcli;

import persistence.Connector;

public class CliMain {

    /**
     * Lancement application computer data base CLI.
     * @param args argsssss
     */
    public static void main(String[] args) {
        try {
            Class.forName(Connector.JDBC_DRIVER);

            Application app = new Application();
            app.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
