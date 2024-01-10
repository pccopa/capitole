package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.validator.currency;

import com.capitole.challenge.ecommerce.prices.domain.exceptions.InvalidCurrencyException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Aspect
@Component
public class CurrencyValidator {

    @Around("@annotation(ValidCurrency)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String currency = (String) args[0];

        try {
            Currency.getInstance(currency);
        } catch (Exception e) {
            throw new InvalidCurrencyException("An invalid ISO currency code has been provided");
        }

        return joinPoint.proceed();
    }
}
