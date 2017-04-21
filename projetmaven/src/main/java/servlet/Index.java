package servlet;

import dto.ComputerDTO;
import services.ComputerServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class Index.
 */

@WebServlet({"", "/index"})
public class Index extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_LENGTH = 20;
    private static final int MAX_COMPUTER_DISPLAYED = 100;
    public static String index = "/WEB-INF/views/dashboard.jsp";


    /**
     * Get informations for computers.
     *
     * @param request  ?
     * @param response ?
     * @throws ServletException ?
     * @throws IOException      ?
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get params
        String search = request.getParameter("search");
        // Page number being displayed
        String pageStr = request.getParameter("currentPage");
        // Length of page of pagination
        String lengthPageStr = request.getParameter("lengthPage");

        // Get the computer displayed
        List<ComputerDTO> computer = null;

        ////////////////////////Parse
        // Params needing parse
        int lengthPageDisplay;
        int pageDisplay;

        // Parsing Page display asked by user
        try {
            pageDisplay = Integer.parseInt(pageStr);
        } catch (Exception e) {
            pageDisplay = 0;
        }

        // Parsing Length asked by user
        try {
            lengthPageDisplay = Integer.parseInt(lengthPageStr);
        } catch (Exception e) {
            lengthPageDisplay = DEFAULT_LENGTH;
        }
        ////////////////////////


        // Verify that length page is valid
        if (lengthPageDisplay <= 0) {
            lengthPageDisplay = DEFAULT_LENGTH;
        } else if (lengthPageDisplay > MAX_COMPUTER_DISPLAYED) {
            lengthPageDisplay = MAX_COMPUTER_DISPLAYED;
        }

        // Get the total count filtered by name
        int totalCount = ComputerServices.getCountComputer(request.getParameter("search"));

        // Check user is at a valid page
        if (pageDisplay < 0) {
            pageDisplay = 0;
        } else if (pageDisplay > totalCount / lengthPageDisplay) {
            pageDisplay = totalCount / lengthPageDisplay;
        }

        // Get the page asked
        computer = ComputerServices.getPagedComputerDTO(pageDisplay, lengthPageDisplay, search);

        // Set all params
        request.setAttribute("computers", computer);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", pageDisplay);
        request.setAttribute("lengthPage", lengthPageDisplay);
        request.setAttribute("search", (search == null ? "" : search));
        getServletContext().getRequestDispatcher(index).forward(request, response);
    }

}
