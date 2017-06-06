package cdb.applicationcli;

import org.springframework.web.context.support.XmlWebApplicationContext;

public class CliMain {

    /**
     * Lancement application computer data base CLI.
     * @param args argsssss
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

}
