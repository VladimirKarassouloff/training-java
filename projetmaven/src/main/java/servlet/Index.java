package servlet;

import bean.BeanParamUtils;
import dto.ComputerDTO;
import model.Computer;
import model.FilterSelect;
import persistence.operator.LikeBoth;
import services.ComputerServices;
import utils.SqlNames;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Index.
 */

@WebServlet({"", "/index"})
public class Index extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_SUCCESS_FORM = "/index";

    // Fallback values
    private static final boolean DEFAULT_ORDER_ASC = false;
    private static final int DEFAULT_LENGTH = 20;
    private static final int MAX_COMPUTER_DISPLAYED = 100;


    public static String index = "/WEB-INF/views/dashboard.jsp";

    private static ComputerServices services = ComputerServices.getInstance();

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
        // Asc or desc ?
        String ascStr = request.getParameter("asc");
        // On order on which column ?
        String colOrder = request.getParameter("colOrder");


        // Get the computer displayed
        List<ComputerDTO> computer = null;

        ////////////////////////Parse
        // Params needing parse
        int lengthPageDisplay;
        int pageDisplay;
        boolean asc;
        int colOrdered;

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

        // Parsing if order is asc or desc
        try {
            asc = Boolean.parseBoolean(ascStr);
        } catch (Exception e) {
            asc = DEFAULT_ORDER_ASC;
        }

        // Parse what column we're ordering
        if (colOrder != null) {
            System.out.println("TODOOOOO");
            System.out.println("TODOOOOO");
            System.out.println("TODOOOOO");
            System.out.println("TODOOOOO");
        }

        ////////////////////////


        // Verify that length page is valid
        if (lengthPageDisplay <= 0) {
            lengthPageDisplay = DEFAULT_LENGTH;
        } else if (lengthPageDisplay > MAX_COMPUTER_DISPLAYED) {
            lengthPageDisplay = MAX_COMPUTER_DISPLAYED;
        }

        // Get the total count filtered by name
        int totalCount = services.getCountComputer(request.getParameter("search"));

        // Check user is at a valid page
        double calc = ((double) totalCount / (double) lengthPageDisplay);
        if (pageDisplay < 0) {
            pageDisplay = 0;
        } else if ((double) pageDisplay >= calc) {
            pageDisplay = (calc % 1 == 0) ? (int) calc - 1 : (int) calc;
        }

        // Get the page asked
        computer = services.getPagedComputerDTO(new FilterSelect.Builder()
                .withPage(pageDisplay)
                .withLengthPage(lengthPageDisplay)
                .withSearch(SqlNames.COMPUTER_COL_JOINED_COMPANY_NAME, new LikeBoth(search))
                .withSearch(SqlNames.COMPUTER_COL_COMPUTER_NAME, new LikeBoth(search))
                .withOrder(colOrder, asc)
                .build()
        );
        //computer = services.getPagedComputerDTO(pageDisplay, lengthPageDisplay, search);
        computer = new ArrayList<>();

        // Set all params
        request.setAttribute("computers", computer);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", pageDisplay);
        request.setAttribute("lengthPage", lengthPageDisplay);
        request.setAttribute("search", (search == null ? "" : search));
        getServletContext().getRequestDispatcher(index).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteUnparsed = req.getParameter("selection");
        if (deleteUnparsed != null) {
            String[] deleteThoseIds = deleteUnparsed.split(",");
            for (String idToDelete : deleteThoseIds) {
                try {
                    int id = Integer.parseInt(idToDelete);
                    services.deleteComputer(new Computer.Builder().withId(id).build());
                } catch (Exception e) {
                    System.err.println("Failed To Parse Id To Delete");
                }
            }
        }
        BeanParamUtils bpu = new BeanParamUtils(req);
        bpu.forget("selection");
        resp.sendRedirect(req.getContextPath() + PAGE_SUCCESS_FORM + bpu.buildUrl());

    }
}
