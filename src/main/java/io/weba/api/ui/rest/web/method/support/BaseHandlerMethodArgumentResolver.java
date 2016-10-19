package io.weba.api.ui.rest.web.method.support;

import io.weba.api.ui.rest.exception.http.BadRequestException;
import io.weba.api.ui.rest.exception.http.InvalidParameterException;
import io.weba.api.ui.rest.exception.http.RequiredParameterNotFoundException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

abstract public class BaseHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    protected String getParameter(String name, Map<String, String[]> parameters) throws BadRequestException {
        if(!parameters.containsKey(name)) {
            throw new RequiredParameterNotFoundException(String.format("%s parameter is required.", name));
        }

        return parameters.get(name)[0];
    }

    protected Date getDateParameter(String name, Map<String, String[]> parameters, DateTimeFormatter dateTimeFormatter, String format) throws BadRequestException {
        try {
            return dateTimeFormatter.parseDateTime(getParameter(name, parameters)).toDate();
        } catch(UnsupportedOperationException | IllegalArgumentException ex) {
            throw new InvalidParameterException(String.format("%s is not valid %s date format", name, format));
        }
    }

    protected UUID getUUIDParameter(String name, Map<String, String[]> parameters) throws BadRequestException {
        try {
            return UUID.fromString(getParameter(name, parameters));
        } catch(IllegalArgumentException ex) {
            throw new InvalidParameterException(String.format("%s is not valid UUID.", name));
        }

    }
}
