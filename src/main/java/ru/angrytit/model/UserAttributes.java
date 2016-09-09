package ru.angrytit.model;

import java.util.stream.Stream;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public enum UserAttributes {
    EMAIL_ATTR("email"),

    NAME_ATTR("name"),

    TITLE_ATTR("custom:title"),

    BUSINESS_NAME("custom:business_name"),

    TYPE("type");

    private final String value;

    UserAttributes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String name) {
        return Stream.of(UserAttributes.values()).filter(each -> each.getValue().equals(name)).findAny().isPresent();
    }
}
