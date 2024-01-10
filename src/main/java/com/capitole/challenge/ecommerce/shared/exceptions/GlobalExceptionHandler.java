package com.capitole.challenge.ecommerce.shared.exceptions;

import com.capitole.challenge.ecommerce.prices.domain.exceptions.InvalidCurrencyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.singleton;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //------------------------
    /**
     * Handles the unknown and unexpected exceptions {@link RuntimeException} and {@link Exception}, then returns a generic response.
     *
     * @param   e           the execption
     * @param   request     the request on which the exception occurred
     * @return              a standard {@link ErrorResponse} with {@link HttpStatus} 500 INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleUncaughtException(final Exception e, final ServletWebRequest request) {
        log.error (e.getLocalizedMessage(), e, request);
        final ErrorResponse response = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .url(request.getRequest().getServletPath())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


//------------------------
    /**
     * Handles the known exceptions {@link RuntimeException} for Entity Not Found Entity and returns a generic response.
     *
     * @param   e           the execption
     * @param   request     the request on which the exception occurred
     * @return              a standard {@link ErrorResponse} with {@link HttpStatus} 404 NOT_FOUND
     */
    @ExceptionHandler(value= {EntityNotFoundException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleKnownException(final RuntimeException e,
                                              final ServletWebRequest request) {
        log.error (e.getLocalizedMessage(), e, request);
        return ErrorResponse.builder()
                .code("NOT_FOUND")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .url(request.getRequest().getServletPath())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }


//------------------------


    /**
     * Handles {@link ConstraintViolationException} exceptions and returns detailed response with
     * every Bean Validation error containing Field name and default message.
     * This includes Param and Entity constraints validation.
     *
     * @param   e           the exception
     * @param   request     the request on which the ex occurred
     * @return              a standard {@link ErrorResponse} with details about validation errors and {@link HttpStatus} 400 BAD_REQUEST
     */
    @ExceptionHandler({ConstraintViolationException.class, InvalidCurrencyException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handleConstraintViolationException(
            final ConstraintViolationException e, final ServletWebRequest request) {
        log.error(e.getLocalizedMessage(), e);

        final Set<ValidationError> invalidParameters = new HashSet<>();
        e.getConstraintViolations().forEach(constraint ->
                constraint.getPropertyPath().iterator().forEachRemaining(n ->
                        invalidParameters.add(new ValidationError(n.getName(), constraint.getMessage()))
                )
        );

        return ErrorResponse.builder()
                .code("UNPROCESSABLE_ENTITY")
                .message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(LocalDateTime.now())
                .url(request.getRequest().getServletPath())
                .errors(invalidParameters)
                .build();
    }


//------------------------

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions and returns detailed response with
     * every Bean Validation error containing Field name and default locale message.
     * This includes: Input Request validation.
     *
     * @param   e           the exception
     * @param   request     the request on which the ex occurred
     * @return              a standard {@link ErrorResponse} with details about validation errors and {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(e.getLocalizedMessage(), e);
        Set<ValidationError> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toSet());

        ErrorResponse response = ErrorResponse.builder()
                .code("BAD_REQUEST")
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }




//------------------------

    /**
     * Handles {@link HttpMessageNotReadableException} exceptions and returns a generic response. Usually for missing RequestBody.
     *
     * @param e         the exception
     * @param request   the request on which the ex occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors and {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(e.getLocalizedMessage(), e);
        ErrorResponse response = ErrorResponse.builder()
                .code("BAD_REQUEST")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

//------------------------
    /**
     * Handles {@link HttpRequestMethodNotSupportedException} exceptions and returns a generic response.
     *
     * @param e         the exception
     * @param request   the request on which the ex occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors and {@link HttpStatus} 405 METHOD_NOT_ALLOWED
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error (e.getLocalizedMessage(), e);
        ErrorResponse response = ErrorResponse.builder()
                .code("METHOD_NOT_ALLOWED")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

//------------------------
    /**
     * Handles {@link ServletRequestBindingException} exceptions and returns a generic response.
     *
     * @param e         the exception
     * @param request   the request on which the exception occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors considering error type and returning {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(e.getMessage(), (ServletWebRequest) request);

        final String missingParameter;
        final String missingParameterType;

        if (e instanceof MissingRequestHeaderException) {
            missingParameter = ((MissingRequestHeaderException) e).getHeaderName();
            missingParameterType = "header";
        } else if (e instanceof MissingServletRequestParameterException) {
            missingParameter = ((MissingServletRequestParameterException) e).getParameterName();
            missingParameterType = "query";
        } else if (e instanceof MissingPathVariableException) {
            missingParameter = ((MissingPathVariableException) e).getVariableName();
            missingParameterType = "path";
        } else {
            missingParameter = "unknown";
            missingParameterType = "unknown";
        }


        ValidationError error = new ValidationError (missingParameter,
                format("Missing %s parameter with name '%s'",
                        missingParameterType,
                        missingParameter)
        );
        var errors = singleton(error);


        final ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message("Missing " + missingParameterType)
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


//------------------------
    /**
     * Handles the uncaught {@link TypeMismatchException} exceptions and returns a generic response.
     *
     * @param e         the exception
     * @param request   the request on which the ex occurred
     * @return          standard {@link ErrorResponse} with details about errors and returning {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(e.getMessage(), e, request);

        String parameter = e.getPropertyName();
        if (e instanceof MethodArgumentTypeMismatchException) {
            parameter = ((MethodArgumentTypeMismatchException) e).getName();
        }

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("BAD_REQUEST")
                .message(format("Wrong type of '%s' parameter. Required '%s'", parameter, e.getRequiredType().getSimpleName()))
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }


//------------------------

    /**
     * Handles the uncaught {@link MissingPathVariableException} exceptions and returns a generic response with details about missing parameters.
     *
     * @param e         the exception
     * @param request   the request on which the ex occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors and returning {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleServletRequestBindingException(e, headers, status, request);
    }


//------------------------

    /**
     * Handles {@link MissingServletRequestParameterException} exceptions and returns a generic response.
     *
     * @param e         the ex
     * @param request   the request on which the ex occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors and returning {@link HttpStatus} 400 BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleServletRequestBindingException(e, headers, status, request);
    }

    /**
     * Handles {@link HttpMediaTypeNotSupportedException} exceptions and returns a generic response.
     *
     * @param e         the e
     * @param request   the request on which the ex occurred
     * @param headers   the headers on which the exception occurred
     * @param status    the status on which the exception occurred
     * @return          standard {@link ErrorResponse} with details about errors and returning {@link HttpStatus} 415 UNSUPPORTED_MEDIA_TYPE
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final ErrorResponse response = ErrorResponse.builder()
                .code("UNSUPPORTED_MEDIA_TYPE")
                .message(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .url(((ServletWebRequest) request).getRequest().getServletPath())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .build();
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

}
