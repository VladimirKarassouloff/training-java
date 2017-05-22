package cdb.servlet;

import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.service.ComputerServiceImpl;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import cdb.utils.UtilsServletError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vkarassouloff on 20/04/17.
 */
@Controller
@RequestMapping("/computer")
public class Computer {

    private static final String PAGE_FORM = "computer";
    private static final String PAGE_SUCCESS_FORM = "index";

    private static final String ATTR_FORM = "formComputer";
    private static final String ATTR_COMPANIES = "companies";
    private static final String ATTR_ERROR = "error";

    private static final String FORM_ID = "id_computer";
    private static final String FORM_NAME = "name_computer";
    private static final String FORM_COMPANY_ID = "company_id_computer";
    private static final String FORM_DATE_INTRODUCED = "introduced_computer";
    private static final String FORM_DATE_DISCONTINUED = "discontinued_computer";

    private IComputerService computerServiceImpl;

    private ICompanyService companyService;


    @Autowired
    public void setComputerServiceImpl(ComputerServiceImpl computerServiceImpl) {
        this.computerServiceImpl = computerServiceImpl;
    }

    @Autowired
    public void setICompanyService(ICompanyService companyService) {
        this.companyService = companyService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model, @RequestParam(value = "id", required = false) Integer idComputer) {

        // Form holder
        ComputerDTO form = null;

        if (idComputer == null) { // Trying to create a new computer
            form = new ComputerDTO();
        } else { // Trying to edit computer
            form = computerServiceImpl.getComputerDTO(idComputer);
        }

        model.addAttribute(ATTR_COMPANIES, companyService.getCompanies());
        model.addAttribute(ATTR_FORM, form);
        model.addAttribute(ATTR_ERROR, "");

        return PAGE_FORM;
    }


    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@ModelAttribute(ATTR_FORM) @Validated ComputerDTO form, BindingResult result, ModelMap model) {
        List<ObjectError> errors = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        errors.stream().map(e -> sb.append(e.toString()));
        String error = sb.toString();

        // Insert or Update if there are no id for the computer
        if (!result.hasErrors()) {
            try {
                if (form.getId() == null) { // Create new Computer
                    computerServiceImpl.formAddComputer(form);
                } else { // Edit a computer
                    computerServiceImpl.formUpdateComputer(form);
                }
            } catch (FormException e) {
                error = e.getMessage();
            }
            return "redirect://" + PAGE_SUCCESS_FORM;
        } else {
            model.addAttribute(ATTR_COMPANIES, companyService.getCompanies());
            model.addAttribute(ATTR_ERROR, error);
            model.addAttribute(ATTR_FORM, form);
            return PAGE_FORM;
        }

    }

}
