package io.weba.api.ui.rest.web.method.support;

import io.weba.api.domain.session.Interval;
import io.weba.api.domain.session.SessionCardinalityCriteria;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

public class SessionCardinalityCriteriaResolver extends BaseHandlerMethodArgumentResolver {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SessionCardinalityCriteria.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();

        return new SessionCardinalityCriteria(
                getDateParameter("dateFrom", parameters, dateTimeFormatter, DATE_FORMAT),
                getDateParameter("dateTo", parameters, dateTimeFormatter, DATE_FORMAT),
                getUUIDParameter("trackerIdentity", parameters),
                Interval.from(request.getParameter("interval"))
        );
    }



}
