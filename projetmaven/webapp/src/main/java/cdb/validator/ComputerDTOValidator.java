package cdb.validator;

import cdb.dto.ComputerDTO;
import cdb.mapper.MapperDate;
import cdb.utils.UtilsValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Service
public class ComputerDTOValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ComputerDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ComputerDTO dto = (ComputerDTO) o;

        if (dto == null) {
            errors.reject("form.null", "Null object. ");
            return;
        }

        if (dto.getName() == null || "".equals(dto.getName())) {
            errors.rejectValue("name", "form.name.required", "The name is required. ");
        }

        Date intro = null;
        if (dto.getIntroduced() != null && !"".equals(dto.getIntroduced())) {
            intro = MapperDate.dateFromString(dto.getIntroduced());
            if (intro == null) {
                errors.rejectValue("introduced", "form.date.notparseable", "Introduced date is not parseable. ");
            }
        }

        Date disc = null;
        if (dto.getDiscontinued() != null && !"".equals(dto.getDiscontinued())) {
            disc = MapperDate.dateFromString(dto.getDiscontinued());
            if (disc == null) {
                errors.rejectValue("discontinued", "form.date.notparseable", "Discontinued date is not parseable. ");
            }
        }

        if (intro != null && intro.before(UtilsValidation.MOST_ANCIENT_DATE)) {
            errors.rejectValue("introduced", "form.date.before1970", "Intro date should be after 1970. ");
        }

        if (disc != null && disc.before(UtilsValidation.MOST_ANCIENT_DATE)) {
            errors.rejectValue("discontinued", "form.date.before1970", "Discontinued date should be after 1970. ");
        }

        if (intro != null && disc != null && intro.after(disc)) {
            errors.reject("form.dateintrobeforedisc", "Intro date should be before discontinued date. ");
        }

    }
}
