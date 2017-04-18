package servlet;

import dto.ComputerDTO;
import model.Computer;
import services.CommonServices;

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

@WebServlet({"", "/Index"})
public class Index extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_LENGTH = 20;

    public static String index = "/WEB-INF/views/dashboard.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * Get request.
     * @param request ?
     * @param response ?
     * @throws ServletException ?
     * @throws IOException ?
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

        // Params needing parse
        int lengthPageDisplay;
        int pageDisplay;

        try{
            pageDisplay = Integer.parseInt(pageStr);
        } catch (Exception e) {
            pageDisplay = 0;
        }

        try {
            lengthPageDisplay = Integer.parseInt(lengthPageStr);
        } catch (Exception e) {
            lengthPageDisplay = DEFAULT_LENGTH;
        }

        // Get the page asked
        computer = CommonServices.getPagedComputerDTO(pageDisplay, lengthPageDisplay, search);


        // Get the total count filtered by name
        int totalCount = CommonServices.getCountComputer(request.getParameter("search"));

        // Set all params
        request.setAttribute("computers", computer);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", pageDisplay);
        request.setAttribute("lengthPage", lengthPageDisplay);
        getServletContext().getRequestDispatcher(index).forward(request, response);
    }

    /**
     * Post request.
     * @param request ?
     * @param response ?
     * @throws ServletException ?
     * @throws IOException ?
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
