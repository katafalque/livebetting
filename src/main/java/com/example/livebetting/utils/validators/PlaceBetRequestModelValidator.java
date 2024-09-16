package com.example.livebetting.utils.validators;

import com.example.livebetting.data.model.request.PlaceBetRequestModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlaceBetRequestModelValidator implements ConstraintValidator<ValidatePlaceBetRequestModel, PlaceBetRequestModel> {

    @Override
    public boolean isValid(PlaceBetRequestModel requestModel, ConstraintValidatorContext context) {
        if (requestModel == null)
            return false;

        return requestModel.getMultiCouponCount() <= 500;
    }
}
