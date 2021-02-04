package com.deviget.devtest.minesweeper.validation;

import com.deviget.devtest.minesweeper.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handle(MethodArgumentNotValidException exception) {
        ErrorResponseDto validationError = createBaseErrorResponseDto();

        List<String> messages = new ArrayList<String>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;

            String violationMessage = buildMessage(fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getRejectedValue().toString(), fieldError.getDefaultMessage());

            messages.add(violationMessage);
        });
        validationError.setMessages(messages);

        return validationError;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDto handle(ConstraintViolationException exception) {
        ErrorResponseDto validationError = createBaseErrorResponseDto();

        List<String> messages = new ArrayList<String>();
        exception.getConstraintViolations().forEach(contraintViolation -> {
            String objectClassName = contraintViolation.getLeafBean().getClass().getSimpleName();
            String propertyPath = contraintViolation.getPropertyPath().toString();
            String field = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);

            String violationMessage = buildMessage(objectClassName, field,
                    contraintViolation.getInvalidValue().toString(), contraintViolation.getMessage());

            messages.add(violationMessage);
        });
        validationError.setMessages(messages);

        return validationError;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponseDto handle(ResponseStatusException exception) {
        ErrorResponseDto validationError = createBaseErrorResponseDto();

        List<String> messages = new ArrayList<String>();
        messages.add(exception.getReason());
        validationError.setMessages(messages);

        return validationError;
    }

    private ErrorResponseDto createBaseErrorResponseDto() {
        ErrorResponseDto errorMessageDto = new ErrorResponseDto();
        errorMessageDto.setTimestamp(LocalDateTime.now());
        errorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessageDto.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return errorMessageDto;
    }

    private String buildMessage(String objectName, String field, String rejectedValue, String message) {
        return "Field error in object '" + objectName + "' on field '" + field + "' with rejected value '"
                + ObjectUtils.nullSafeToString(rejectedValue) + "': " + message;
    }
}
