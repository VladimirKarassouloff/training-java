package cdb.applicationcli;

import cdb.applicationcli.pages.Page;
import cdb.applicationcli.pages.MainPage;

import java.util.Scanner;
import java.util.Stack;

public class Application {


    public Stack<Page> pages;

    /**
     * Consturctor.
     */
    public Application() {
        pages = new Stack<Page>();
        pages.push(new MainPage(this));
    }

    /**
     * Remove current page, and send event to the new page on top.
     */
    public void popPage() {
        this.pages.pop();
        if (this.pages.size() > 0) {
            this.pages.peek().onFirstGroundEvent();
        }
    }

    /**
     * Add page on top of the application.
     *
     * @param p new Page on top
     */
    public void pushPage(Page p) {
        this.pages.push(p);
    }

    /**
     * Run the application.
     */
    public void run() {

        Scanner input = new Scanner(System.in);
        String command = null;

        do {
            if (command != null) {
                pages.peek().computeCommand(command);
            }
            if (pages.size() > 0) {
                pages.peek().printPageInfos();
            }

        } while (this.pages.size() > 0 && (command = input.next()) != null);

    }


}
