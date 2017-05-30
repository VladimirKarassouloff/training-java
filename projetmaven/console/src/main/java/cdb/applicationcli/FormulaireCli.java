package cdb.applicationcli;

import cdb.exception.FormException;
import cdb.mapper.MapperDate;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Scanner;

public class FormulaireCli {


    public static Scanner input = new Scanner(System.in);

    @Autowired
    public static IComputerService computerService;

    @Autowired
    public static ICompanyService companyService;

    /**
     * Ask user to give a date formatted like yyyy-MM-dd.
     *
     * @return Date nullable
     */
    public static Date reclaimDateOrNullInput() {

        while (true) {

            String inputUser = input.nextLine();

            if (inputUser.toLowerCase().equals("null")) {
                return null;
            } else {
                Date d = MapperDate.dateFromString(inputUser);
                if (d != null) {
                    return d;
                } else {
                    System.out.println("Date invalide");
                }
            }

        }
    }

    /**
     * Ask user an Integer as input contained in a range.
     *
     * @param startRange start range (inclusive)
     * @param endRange   end range (inclusive)
     * @return integer
     */
    public static Integer reclaimIntInputBetweenRange(int startRange, int endRange) {

        while (true) {

            try {

                int inputInt = input.nextInt();
                if (inputInt >= startRange && inputInt <= endRange) {
                    return inputInt;
                } else {
                    System.out.println("La valeur devrait etre entre " + startRange + " et " + endRange);
                }

            } catch (RuntimeException e) {
                System.out.println("Error : " + e.getMessage());
            }

        }

    }

    /**
     * Ask user an Integer as input contained in a range, or 'null'.
     *
     * @param startRange start range (inclusive)
     * @param endRange   end range (inclusive)
     * @return integer nullable
     */
    public static Integer reclaimIntOrNullInputBetweenRange(int startRange, int endRange) {

        while (true) {

            Integer i = reclaimIntOrNullInput();
            if (i == null) {
                return null;
            }
            if (i >= startRange && i <= endRange) {
                return i;
            } else {
                System.out.println("La valeur devrait etre entre " + startRange + " et " + endRange);
            }

        }
    }

    /**
     * Ask user an Integer as input or 'null'.
     *
     * @return integer
     */
    public static Integer reclaimIntOrNullInput() {

        while (true) {

            String inputUser = input.nextLine();
            if (inputUser.toLowerCase().equals("null")) {
                return null;
            } else {
                try {
                    return Integer.parseInt(inputUser);
                } catch (NumberFormatException e) {
                    System.out.println("Mauvais format de date");
                }
            }

        }
    }

    /**
     * Basic form for creating a computer without company.
     *
     * @return computer
     */
    public static Computer precreateComputer() {
        // Vars
        String nameInput;
        Integer lineChoosen;
        Date start, end;

        // Form
        // Nom
        System.out.println("Entrez le nom");
        nameInput = FormulaireCli.input.nextLine();
        // Date commercial
        System.out.println("Entrez la date de commercialisation");
        start = FormulaireCli.reclaimDateOrNullInput();
        // Date fin commercial
        System.out.println("Entrez la date de fin de commercialisation");
        end = FormulaireCli.reclaimDateOrNullInput();


        return new Computer(null, nameInput, start, end);
    }

    /**
     * Basic form to update the introduced date of a computer.
     *
     * @param computer to update
     */
    public static void updateComputerIntroduced(Computer computer) {
        System.out.println("Entrez la nouvelle date");
        Date newDate = FormulaireCli.reclaimDateOrNullInput();
        computer.setIntroduced(newDate);
        try {
            computerService.updateComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer");
        }
    }

    /**
     * Basic form to update the discontinued date of a computer.
     *
     * @param computer to update
     */
    public static void updateComputerDiscontinuedDate(Computer computer) {
        System.out.println("Entrez la nouvelle date");
        Date newDate = FormulaireCli.reclaimDateOrNullInput();
        computer.setDiscontinued(newDate);
        try {
            computerService.updateComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer : " + e.getMessage());
        }
    }

    /**
     * Basic form to update the name of a computer.
     *
     * @param computer to update
     */
    public static void updateComputerName(Computer computer) {
        System.out.println("Entrez le nouveau nom");
        computer.setName(FormulaireCli.input.nextLine());
        try {
            computerService.updateComputer(computer);
        } catch (FormException e) {
            System.out.println("Error while updating computer");
        }
    }

    /**
     * Basic form to update the introduced date of a company.
     *
     * @param company to update
     */
    public static void updateCompanyName(Company company) {
        System.out.println("Entrez le nouveau nom");
        company.setName(FormulaireCli.input.nextLine());
        companyService.updateCompany(company);
    }


}
