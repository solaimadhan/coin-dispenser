package com.project.coindispenser.util;

import java.util.ArrayList;
import java.util.List;

import com.project.coindispenser.model.Bill;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BillValueValidator implements ConstraintValidator<BillValue, String> {

    private List<Integer> expectedValues = new ArrayList<>(5);
    private String returnMessage = null;

    @Override
    public void initialize(BillValue billValue) {

        Enum<Bill>[] billss = billValue.enumClass().getEnumConstants();
        for (Enum<Bill> b : billss) {
            expectedValues.add(((Bill)b).value());
        }
        returnMessage = billValue.message().concat(expectedValues.toString());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean error = false;
        try {
            int intValue = Integer.parseInt(value);
            if (!Bill.isValid(intValue)) {
                error = true;
            }
        } catch (NumberFormatException nfe) {
            error = true;
        }

        if (error) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(returnMessage)
                        .addConstraintViolation();
            return false;
        }
        return true;
    }

}
