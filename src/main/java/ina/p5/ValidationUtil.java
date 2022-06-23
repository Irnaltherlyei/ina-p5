package ina.p5;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationUtil<BeanType> {
    ValidatorFactory validatorFactory;
    Validator validator;

    private Set<ConstraintViolation<BeanType>> violations;

    /**
     * Checks if a bean is valid.
     */
    public ValidationUtil() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Boolean isValid(BeanType bean) {
        violations = validator.validate(bean);
        return violations.isEmpty();
    }

    public Set<ConstraintViolation<BeanType>> getViolations() {
        return violations;
    }
}
