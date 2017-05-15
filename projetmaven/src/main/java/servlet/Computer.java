package servlet;

import dto.ComputerDTO;
import exception.FormException;
import services.CompanyServices;
import services.ComputerServices;
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

    private static ComputerServices serviceComputer = ComputerServices.getInstance();
    private static CompanyServices serviceCompany = CompanyServices.getInstance();

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
                form = serviceComputer.getComputerDTO(parsedIdComputer);

                // Computer not found while giving a precise id, error, redirect to 404
                if (form == null) {
                    request.setAttribute(UtilsServletError.NAME_ATTRIBUTE_ERROR, "Computer not found");
                    request.getRequestDispatcher(UtilsServletError.ERROR_404).forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // If id cannot be parsed
                request.setAttribute(UtilsServletError.NAME_ATTRIBUTE_ERROR, "Invalid id");
                request.getRequestDispatcher(UtilsServletError.ERROR_403).forward(request, response);
                return;
            }
        }

        request.setAttribute(ATTR_COMPANIES, serviceCompany.getCompanies());
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

        // Error to display on form-page if any
        String error = null;

        // Get back the form
        Integer id = null;
        Integer companyId = null;

        // Parse Form id computer to check whether it's an edit or an add
        if (request.getParameter(FORM_ID) == null || request.getParameter(FORM_ID).equals("")) {
            id = null;
        } else {
            try {
                id = Integer.parseInt(request.getParameter(FORM_ID));
            } catch (Exception e) {
                error = "Error parsing computer id";
            }
        }

        // Parse Form id company
        if (request.getParameter(FORM_COMPANY_ID) == null || request.getParameter(FORM_COMPANY_ID).equals("")) {
            companyId = null;
        } else {
            try {
                companyId = Integer.parseInt(request.getParameter(FORM_COMPANY_ID));
            } catch (Exception e) {
                error = "Error parsing company id";
            }
        }

        // Build computerdto from form
        ComputerDTO form = new ComputerDTO.Builder()
                .withId(id)
                .withName(request.getParameter(FORM_NAME))
                .withIntroducedDate(request.getParameter(FORM_DATE_INTRODUCED))
                .withDiscontinuedDate(request.getParameter(FORM_DATE_DISCONTINUED))
                .withCompanyId(companyId)
                .build();

        // Insert or Update if there are no id-parsing error
        if (error == null) {
            try {
                if (form.getId() == null) { // Create new Computer
                    serviceComputer.formAddComputer(form);
                } else { // Edit a computer
                    serviceComputer.formUpdateComputer(form);
                }
            } catch (FormException e) {
                error = e.getMessage();
            }
        }

        // Sendback to page if error, otherwise redirect to dashboard
        if (error != null) {
            request.setAttribute(ATTR_COMPANIES, serviceCompany.getCompanies());
            request.setAttribute(ATTR_ERROR, error);
            request.setAttribute(ATTR_FORM, form);
            request.getRequestDispatcher(PAGE_FORM).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + PAGE_SUCCESS_FORM);
        }

    }

}
