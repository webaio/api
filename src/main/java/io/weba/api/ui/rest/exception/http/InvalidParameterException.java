package io.weba.api.ui.rest.exception.http;

public class InvalidParameterException extends BadRequestException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
