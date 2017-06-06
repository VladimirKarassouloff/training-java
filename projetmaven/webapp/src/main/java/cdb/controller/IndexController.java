package cdb.controller;

import cdb.bean.BeanParamUtils;
import cdb.dto.ComputerDTO;
import cdb.mapper.MapperComputer;
import cdb.model.Computer;
import cdb.service.IComputerService;
import cdb.utils.UtilsServletError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = {"", "/index"})
public class IndexController {
    private static final long serialVersionUID = 1L;

    public static final String INDEX = "dashboard";

    private static final String DEFAULT_LENGTH = "20";
    private static final String DEFAULT_ORDER_ASC = "true";
    private static final String DEFAULT_ORDERED_COL = "0";

    private IComputerService services;

    private MapperComputer mapperComputer;

    @Autowired
    public void setServices(IComputerService services) {
        this.services = services;
    }

    @Autowired
    public void setMapperComputer(MapperComputer mapperComputer) {
        this.mapperComputer = mapperComputer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "currentPage", defaultValue = "0") int pageDisplay,
                        @RequestParam(value = "lengthPage", defaultValue = DEFAULT_LENGTH) int lengthPageDisplay,
                        @RequestParam(value = "asc", defaultValue = DEFAULT_ORDER_ASC) boolean asc,
                        @RequestParam(value = "colOrder", defaultValue = DEFAULT_ORDERED_COL) int colOrder) {



        // Get the datas for the page
        Page<ComputerDTO> cp = services.getPage(new PageRequest(pageDisplay, lengthPageDisplay,
                (asc ? Sort.Direction.ASC : Sort.Direction.DESC), Computer.colToProperty(colOrder)), search).map(mapperComputer::toDTO);

        // Set all attributes
        model.addAttribute("asc", asc);
        model.addAttribute("computers", cp.getContent());
        model.addAttribute("totalCount", cp.getTotalElements());
        model.addAttribute("currentPage", cp.getNumber());
        model.addAttribute("lengthPage", cp.getSize());
        model.addAttribute("search", (search == null ? "" : search));

        return INDEX;
    }


    @RequestMapping(method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                         @RequestParam(value = "selection", required = false) String deleteUnparsed) {

        if (deleteUnparsed != null) {
            String[] deleteThoseIds = deleteUnparsed.split(",");
            long[] parsedId = new long[deleteThoseIds.length];
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
        bpu.forget("selection");
        return "redirect://index" + bpu.buildUrl();
    }

}
