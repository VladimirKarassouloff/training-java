package cdb;


import cdb.service.ComputerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vkarassouloff on 17/05/17.
 */
public class Application {

    @Autowired
    public static ComputerServiceImpl computerService;


    /**
     * Launch spring app.
     *
     * @param args params
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
        System.out.println(computerService.getComputerDaoImpl());
    }
}
