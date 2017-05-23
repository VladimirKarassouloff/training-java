package cdb.validator;

import cdb.dto.ComputerDTO;
import cdb.mapper.MapperDate;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

import static cdb.utils.UtilsSql.MOST_ANCIENT_DATE;

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
            errors.reject("Null object. ");
            return;
        }

        if (dto.getName() == null || "".equals(dto.getName())) {
            errors.reject("The name is required. ");
        }

        Date intro = null;
        if (dto.getIntroduced() != null && !"".equals(dto.getIntroduced())) {
            intro = MapperDate.dateFromString(dto.getIntroduced());
            if (intro == null) {
                errors.reject("Introduced date is not parseable. ");
            }
        }

        Date disc = null;
        if (dto.getDiscontinued() != null && !"".equals(dto.getDiscontinued())) {
            disc = MapperDate.dateFromString(dto.getDiscontinued());
            if (disc == null) {
                errors.reject("Discontinued date is not parseable. ");
            }
        }

        if (intro != null && intro.before(MOST_ANCIENT_DATE)) {
            errors.reject("Intro date should be after 1970. ");
        }

        if (disc != null && disc.before(MOST_ANCIENT_DATE)) {
            errors.reject("Discontinued date should be after 1970. ");
        }

        if (intro != null && disc != null && intro.after(disc)) {
            errors.reject("Intro date should be before discontinued date. ");
        }

    }
}
