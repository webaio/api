package io.weba.api.ui.rest.exception.http;

public class RequiredParameterNotFoundException extends BadRequestException {
    public RequiredParameterNotFoundException(String message) {
        super(message);
    }
}
