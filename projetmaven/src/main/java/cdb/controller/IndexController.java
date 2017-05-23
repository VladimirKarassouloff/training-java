package cdb.controller;

import cdb.bean.BeanParamUtils;
import cdb.model.ComputerPage;
import cdb.persistence.filter.FilterSelectComputer;
import cdb.persistence.operator.LikeRight;
import cdb.service.IComputerService;
import cdb.utils.SqlNames;
import cdb.utils.UtilsServletError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Index.
 */

@Controller
@RequestMapping(value = {"", "/index"})
public class IndexController {
    private static final long serialVersionUID = 1L;

    public static final String INDEX = "dashboard";

    private static final String DEFAULT_LENGTH = "20";


    private IComputerService services;

    @Autowired
    public void setServices(IComputerService services) {
        this.services = services;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "currentPage", defaultValue = "0") int pageDisplay,
                        @RequestParam(value = "lengthPage", defaultValue = DEFAULT_LENGTH) int lengthPageDisplay,
                        @RequestParam(value = "asc", required = false) Boolean asc,
                        @RequestParam(value = "colOrder", required = false) Integer colOrder) {


        // Filter for counting and selecting
        FilterSelectComputer.Builder builder = new FilterSelectComputer.Builder();

        // Is the user reasearching by name or company name ?
        if (search != null && !"".equals(search)) {
            builder.withSearch(SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_NAME, new LikeRight(search))
                    .withSearch(SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME, new LikeRight(search));
        }

        // Is there a request for ordered data ?
        if (asc != null && colOrder != null) {
            builder.withOrder(colOrder, asc);
        }

        // Get the datas for the page
        try {
            ComputerPage cp = services.getPage(builder.withPage(pageDisplay)
                    .withLengthPage(lengthPageDisplay)
                    .build());

            // Set all attributes
            model.addAttribute("computers", cp.getResults());
            model.addAttribute("totalCount", cp.getTotalCount());
            model.addAttribute("currentPage", cp.getDisplayedPage());
            model.addAttribute("lengthPage", lengthPageDisplay);
            model.addAttribute("search", (search == null ? "" : search));
            return INDEX;
        } catch (RuntimeException e) {
            return UtilsServletError.ERROR_500;
        }

    }


    @RequestMapping(method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         @RequestParam(value = "selection", required = false) String deleteUnparsed) {

        if (deleteUnparsed != null) {
            String[] deleteThoseIds = deleteUnparsed.split(",");
            int[] parsedId = new int[deleteThoseIds.length];
            for (int i = 0; i < parsedId.length; i++) {
                try {
                    parsedId[i] = Integer.parseInt(deleteThoseIds[i]);
                } catch (NumberFormatException e) {
                    return UtilsServletError.ERROR_500;
                }
            }
            services.deleteComputer(parsedId);
        }

        BeanParamUtils bpu = new BeanParamUtils(request);
        return "redirect://" + INDEX + bpu.buildUrl();
    }

}
