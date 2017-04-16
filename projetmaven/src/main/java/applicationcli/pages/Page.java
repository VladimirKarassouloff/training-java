package applicationcli.pages;

import applicationcli.Application;

import java.util.Scanner;

public abstract class Page {

    protected Application app;
    protected Scanner input;

    /**
     * Constructor.
     * @param app belonging to
     */
    public Page(Application app) {
        super();
        this.app = app;
        this.input = new Scanner(System.in);
    }

    /**
     * Info displayed in the page.
     */
    public abstract void printPageInfos();

    /**
     * React to user input.
     * @param command input
     */
    public abstract void computeCommand(String command);

    /**
     * Event triggered when the page is in background and its upper page get's popped.
     */
    public void onFirstGroundEvent() {

    }

}
