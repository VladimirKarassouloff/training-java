package servlet;

import bean.BeanParamUtils;
import model.ComputerPage;
import persistence.filter.FilterSelectComputer;
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
    public static final String INDEX = "/WEB-INF/views/dashboard.jsp";

    // Fallback values
    private static final boolean DEFAULT_ORDER_ASC = false;
    private static final int DEFAULT_LENGTH = 20;
    private static final int MAX_COMPUTER_DISPLAYED = 100;
    private static final int DEFAULT_COL_ORDERED = 0;


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
        /////// Get all params
        // Search by computer or company name
        String search = request.getParameter("search");
        // Page number being displayed
        String pageStr = request.getParameter("currentPage");
        // Length of page of pagination
        String lengthPageStr = request.getParameter("lengthPage");
        // Asc or desc ?
        String ascStr = request.getParameter("asc");
        // On order on which column ?
        String colOrderStr = request.getParameter("colOrder");

        // Params needing parse
        int lengthPageDisplay;
        int pageDisplay;
        int numColOrder;
        boolean asc;

        //////////////////////// Parsing
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

        // Parsing which column is ordered
        try {
            numColOrder = Integer.parseInt(colOrderStr);
        } catch (Exception e) {
            numColOrder = DEFAULT_COL_ORDERED;
        }

        ////////////////////////


        // Verify that length page is valid
        if (lengthPageDisplay <= 0) {
            lengthPageDisplay = DEFAULT_LENGTH;
        } else if (lengthPageDisplay > MAX_COMPUTER_DISPLAYED) {
            lengthPageDisplay = MAX_COMPUTER_DISPLAYED;
        }

        // Filter for counting and selecting
        FilterSelectComputer.Builder builder = new FilterSelectComputer.Builder();
        if (search != null) {
            builder.withSearch(SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_NAME, new LikeBoth(search))
                    .withSearch(SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME, new LikeBoth(search));
        }

        // Get the datas for the page
        ComputerPage cp = services.getPage((FilterSelectComputer) builder.withPage(pageDisplay)
                .withLengthPage(lengthPageDisplay)
                .withOrder(numColOrder, asc)
                .build());

        // Set all attributes
        request.setAttribute("computers", cp.getResults());
        request.setAttribute("totalCount", cp.getTotalCount());
        request.setAttribute("currentPage", cp.getDisplayedPage());
        request.setAttribute("lengthPage", lengthPageDisplay);
        request.setAttribute("search", (search == null ? "" : search));
        getServletContext().getRequestDispatcher(INDEX).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteUnparsed = req.getParameter("selection");
        if (deleteUnparsed != null) {
            String[] deleteThoseIds = deleteUnparsed.split(",");
            List<Integer> idsParsed = new ArrayList<>();
            for (String idToDelete : deleteThoseIds) {
                idsParsed.add(Integer.parseInt(idToDelete));
            }

            services.deleteComputer(idsParsed);
        }

        BeanParamUtils bpu = new BeanParamUtils(req);
        bpu.forget("selection");
        resp.sendRedirect(req.getContextPath() + PAGE_SUCCESS_FORM + bpu.buildUrl());

    }
}
