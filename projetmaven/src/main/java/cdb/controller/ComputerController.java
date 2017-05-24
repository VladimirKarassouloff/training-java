package cdb.controller;

import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.mapper.MapperComputer;
import cdb.mapper.MapperComputerDTO;
import cdb.service.ComputerServiceImpl;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import cdb.validator.ComputerDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    private static final String PAGE_FORM = "computer";
    private static final String PAGE_SUCCESS_FORM = "index";

    private static final String ATTR_FORM = "formComputer";
    private static final String ATTR_COMPANIES = "companies";
    private static final String ATTR_ERROR = "error";

    private IComputerService computerService;

    private ICompanyService companyService;

    private ComputerDTOValidator computerValidator;

    @Autowired
    public void setComputerServiceImpl(IComputerService computerService) {
        this.computerService = computerService;
    }

    @Autowired
    public void setICompanyService(ICompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Inject validator for computer.
     *
     * @param binder holder of validator
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model, @RequestParam(value = "id", required = false) Integer idComputer) {

        // Form holder
        ComputerDTO form = null;

        if (idComputer == null) { // Trying to create a new computer
            form = new ComputerDTO();
        } else { // Trying to edit computer
            form = MapperComputer.toDTO(computerService.getComputer(idComputer));
        }

        model.addAttribute(ATTR_COMPANIES, companyService.getCompanies());
        model.addAttribute(ATTR_FORM, form);
        model.addAttribute(ATTR_ERROR, "");

        return PAGE_FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@ModelAttribute(ATTR_FORM) @Valid ComputerDTO form, BindingResult result, ModelMap model) {
        String error = null;

        // Insert or Update if there are no id for the computer
        if (!result.hasErrors()) {
            try {
                if (form.getId() == null) { // Create new Computer
                    computerService.formAddComputer(form);
                } else { // Edit a computer
                    computerService.formUpdateComputer(form);
                }
                return "redirect://" + PAGE_SUCCESS_FORM;
            } catch (FormException e) {
                error = e.getMessage();
            }
        }

        // Exception during add/edit computer, go back to form
        model.addAttribute(ATTR_COMPANIES, companyService.getCompanies());
        model.addAttribute(ATTR_ERROR, error);
        model.addAttribute(ATTR_FORM, form);

        return PAGE_FORM;
    }

}
