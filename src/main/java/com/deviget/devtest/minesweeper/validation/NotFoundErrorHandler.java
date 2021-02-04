package com.deviget.devtest.minesweeper.validation;

import com.deviget.devtest.minesweeper.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class NotFoundErrorHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDto handle(NoSuchElementException exception) {
        ErrorResponseDto validationError = createBaseErrorResponseDto();

        List<String> messages = new ArrayList<String>();
        messages.add(exception.getMessage());
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
}
