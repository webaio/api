package io.weba.api.domain.criteria;

public enum Parameter {
    DATE_FROM("dateFrom"),
    DATE_TO("dateTo");

    String name;

    Parameter(String name) {
        this.name = name;
    }
}
