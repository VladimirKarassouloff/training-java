package servlet;

import dto.ComputerDTO;
import services.CompanyServices;
import services.ComputerServices;
import sun.security.pkcs.ParsingException;
import utils.UtilsServletError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vkarassouloff on 20/04/17.
 */
@WebServlet({"/computer"})
public class Computer extends HttpServlet {

    private static final String PAGE_FORM = "/WEB-INF/views/computer.jsp";
    private static final String PAGE_SUCCESS_FORM = "/index";

    private static final String ATTR_FORM = "form";
    private static final String ATTR_COMPANIES = "companies";
    private static final String ATTR_ERROR = "error";

    private static final String FORM_ID = "id_computer";
    private static final String FORM_NAME = "name_computer";
    private static final String FORM_COMPANY_ID = "company_id_computer";
    private static final String FORM_DATE_INTRODUCED = "introduced_computer";
    private static final String FORM_DATE_DISCONTINUED = "discontinued_computer";


    /**
     * Present form for adding / editing computer.
     *
     * @param request  ?
     * @param response ?
     * @throws ServletException ?
     * @throws IOException      ?
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Form holder
        ComputerDTO form = null;

        // Check Id parameter
        String idComputer = request.getParameter("id");


        if (idComputer == null) { // Trying to create a new computer
            form = new ComputerDTO();
        } else { // Trying to edit computer

            try {
                int parsedIdComputer = Integer.parseInt(idComputer);
                form = ComputerServices.getComputerDTO(parsedIdComputer);

                // Computer not found while giving a precise id, error, redirect to 404
                if (form == null) {
                    request.setAttribute(UtilsServletError.NAME_ATTRIBUTE_ERROR, "Computer not found");
                    request.getRequestDispatcher(UtilsServletError.ERROR_404).forward(request, response);
                    return;
                }
            } catch (ParsingException e) {
                // If id cannot be parsed
                request.setAttribute(UtilsServletError.NAME_ATTRIBUTE_ERROR, "Invalid id");
                request.getRequestDispatcher(UtilsServletError.ERROR_403).forward(request, response);
                return;
            }
        }

        request.setAttribute(ATTR_COMPANIES, CompanyServices.getCompanies());
        request.setAttribute(ATTR_FORM, form);
        request.setAttribute(ATTR_ERROR, "");
        getServletContext().getRequestDispatcher(PAGE_FORM).forward(request, response);
    }

    /**
     * Do computer creation/edition or redirect to the page if an error occured.
     *
     * @param request  ?
     * @param response ?
     * @throws ServletException ?
     * @throws IOException      ?
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get back the form
        Integer id = Integer.parseInt(request.getParameter(FORM_ID));
        Integer companyId;
        if (request.getParameter(FORM_COMPANY_ID) == null || request.getParameter(FORM_COMPANY_ID).equals("null")) {
            companyId = null;
        } else {
            companyId = Integer.parseInt(request.getParameter(FORM_COMPANY_ID));
        }
        ComputerDTO form = new ComputerDTO.Builder()
                .withId(id)
                .withName(request.getParameter(FORM_NAME))
                .withIntroducedDate(request.getParameter(FORM_DATE_INTRODUCED))
                .withDiscontinuedDate(request.getParameter(FORM_DATE_DISCONTINUED))
                .withCompanyId(companyId)
                .build();


        // Insert or Update
        String error = null;
        try {
            if (form.getId() == null) { // Create new Computer
                ComputerServices.formAddComputer(form);
            } else { // Edit a computer
                ComputerServices.formUpdateComputer(form);
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        // Sendback to page if error, otherwise redirect to dashboard
        if (error != null) { request.setAttribute(ATTR_COMPANIES, CompanyServices.getCompanies());
            request.setAttribute(ATTR_ERROR, error);
            request.setAttribute(ATTR_FORM, form);
            request.getRequestDispatcher(PAGE_FORM).forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + PAGE_SUCCESS_FORM);
        }


    }

}
