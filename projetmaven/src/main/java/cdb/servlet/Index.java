package cdb.servlet;

import cdb.bean.BeanParamUtils;
import cdb.model.ComputerPage;
import cdb.persistence.filter.FilterSelectComputer;
import cdb.persistence.operator.LikeRight;
import cdb.service.ComputerServiceImpl;
import cdb.utils.SqlNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private ComputerServiceImpl services;


    @Autowired
    public void setServices(ComputerServiceImpl services) {
        this.services = services;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }


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
        } catch (NumberFormatException e) {
            pageDisplay = 0;
        }

        // Parsing Length asked by user
        try {
            lengthPageDisplay = Integer.parseInt(lengthPageStr);
        } catch (NumberFormatException e) {
            lengthPageDisplay = DEFAULT_LENGTH;
        }

        // Parsing if order is asc or desc
        asc = Boolean.parseBoolean(ascStr);

        // Parsing which column is ordered
        try {
            numColOrder = Integer.parseInt(colOrderStr);
        } catch (NumberFormatException e) {
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

        // Is the user reasearching by name or company name ?
        if (search != null && !"".equals(search)) {
            builder.withSearch(SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_NAME, new LikeRight(search))
                    .withSearch(SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME, new LikeRight(search));
        }

        // Is there a request for ordered data ?
        if (ascStr != null || colOrderStr != null) {
            builder.withOrder(numColOrder, asc);
        }

        // Get the datas for the page
        try {
            ComputerPage cp = services.getPage((FilterSelectComputer) builder.withPage(pageDisplay)
                    .withLengthPage(lengthPageDisplay)
                    .build());

            // Set all attributes
            request.setAttribute("computers", cp.getResults());
            request.setAttribute("totalCount", cp.getTotalCount());
            request.setAttribute("currentPage", cp.getDisplayedPage());
            request.setAttribute("lengthPage", lengthPageDisplay);
            request.setAttribute("search", (search == null ? "" : search));
            getServletContext().getRequestDispatcher(INDEX).forward(request, response);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deleteUnparsed = req.getParameter("selection");
        if (deleteUnparsed != null) {
            String[] deleteThoseIds = deleteUnparsed.split(",");
            int[] parsedId = new int[deleteThoseIds.length];
            for (int i = 0; i < parsedId.length; i++) {
                try {
                    parsedId[i] = Integer.parseInt(deleteThoseIds[i]);
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
            services.deleteComputer(parsedId);
        }

        BeanParamUtils bpu = new BeanParamUtils(req);
        bpu.forget("selection");
        resp.sendRedirect(req.getContextPath() + PAGE_SUCCESS_FORM + bpu.buildUrl());

    }

}
