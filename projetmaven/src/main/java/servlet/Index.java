package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Computer;
import services.CommonServices;

/**
 * Servlet implementation class Index
 */

@WebServlet({"", "/Index"})
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public static String index = "/WEB-INF/views/dashboard.jsp";
	public static int NUMBER_RESULT_PER_PAGE = 25;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get the computer displayed
		List<Computer> computer = null;
		try {
			int pageDisplay = Integer.parseInt(request.getParameter("page"));
			computer = CommonServices.getPagedComputer(pageDisplay, NUMBER_RESULT_PER_PAGE);
		} catch(Exception e) {
			computer = CommonServices.getPagedComputer(0, NUMBER_RESULT_PER_PAGE);
		} 

		// Get the total count filtered by name
		System.out.println("Search by name  : " + request.getParameter("search"));
		int totalCount = CommonServices.getCountComputer(request.getParameter("search"));

		request.setAttribute("computers", computer);
		getServletContext().getRequestDispatcher(index).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
