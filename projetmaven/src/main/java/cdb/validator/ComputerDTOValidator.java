package cdb.validator;

import cdb.dto.ComputerDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by vkarassouloff on 22/05/17.
 */
public class ComputerDTOValidator implements ConstraintValidator<ComputerDTO,String> {


    @Override
    public void initialize(ComputerDTO computerDTO) {
        System.out.println("mdr");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("xD");
        return false;
    }
}
