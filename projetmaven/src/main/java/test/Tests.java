package test;

import model.FilterSelect;
import persistence.ComputerDAO;
import persistence.operator.Equal;
import persistence.operator.LikeBoth;

public class Tests {

    /**
     * Tests.
     *
     * @param args argssss
     */
    public static void main(String[] args) {
        try {

            ComputerDAO computerDao = ComputerDAO.getInstance();
            System.out.println();
            System.out.println();
            System.out.println();
            FilterSelect fs = new FilterSelect.Builder()
                    .withSearch(ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_NAME, new LikeBoth("app"))
                    .withOrder(ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_NAME,true)
                    .withLengthPage(1)
                    .build();
            System.out.println(computerDao.getFromFilter(fs).get(0));
            System.out.println();

            FilterSelect fs2 = new FilterSelect.Builder()
                    .withSearch(ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_NAME, new LikeBoth("app"))
                    .withOrder(ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_NAME,false)
                    .withLengthPage(1)
                    .build();
            System.out.println(computerDao.getFromFilter(fs2).get(0));
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();

            FilterSelect fs3 = new FilterSelect.Builder()
                    .withSearch(ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_NAME, new Equal("Apple Lisa"))
                    .withOrder(ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_NAME,false)
                    .withLengthPage(2)
                    .build();
            System.out.println(computerDao.getFromFilter(fs3));
            System.out.println(computerDao.getFromFilter(fs3).size());
            System.out.println();
            System.out.println();
            //Connector c = Connector.getInstance();
            //System.out.println(c);
            /*
            System.out.println("Up Computer");
            Computer comp = CompanyServices.getComputer(73);
            comp.setDiscontinued(new Date());
            comp.setCompany(CompanyServices.getCompany(1));
            comp.setName("Upated");
            comp.setIntroduced(null);
            System.out.println(ComputerDAO.update(comp));
            System.out.println("Get All companies");
            System.out.println(CompanyDAO.getAll());
            System.out.println();
            System.out.println();
            System.out.println("Get company by id = 1");
            System.out.println(CompanyDAO.getById(1));
            System.out.println();
            System.out.println();
            System.out.println("Get All computers");
            System.out.println(ComputerDAO.getAll());
            System.out.println();
            System.out.println();
            System.out.println("Get computer by id = 20");
            System.out.println(ComputerDAO.getById(20));
            System.out.println("Insert Computer");
            Computer comp = new Computer(MapperCompany.mapResultSetToObject(CompanyDAO.getById(2)), "mdr 2.0", new Date(), new Date());
            System.out.println(ComputerDAO.insert(comp));
            System.out.println();
            System.out.println("L'id generee est " + comp.getId());
            System.out.println("Delete compute by id = " + comp.getId());
            System.out.println("Succes ? : " + ComputerDAO.deleteById(comp.getId()));
            System.out.println();
            System.out.println("Delete d'une fake id");
            System.out.println("Succes ? : " + ComputerDAO.deleteById(20202));
            System.out.println("Get computer by id = " + comp.getId());
            System.out.println(ComputerDAO.getById(comp.getId()));
            System.out.println("Get du count des Company");
            System.out.println(CompanyDAO.getCount());
            System.out.println("Get des 0-10 premieres company Company");
            System.out.println(CompanyDAO.getPagination(0, 10));
            System.out.println("Get des 10-20 premieres company Company");
            System.out.println(CompanyDAO.getPagination(1, 10));
            System.out.println("Get des 0-10 premieres computer Computer");
            System.out.println(ComputerDAO.getPagination(0, 10));
            System.out.println("Get des 0-20 premieres computer Computer");
            System.out.println(ComputerDAO.getPagination(0, 20));
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
