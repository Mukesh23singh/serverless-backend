package ru.angrytit.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public enum UserAttributes {
    EMAIL_ATTR("email"),

    NAME_ATTR("name"),

    TITLE_ATTR("custom:title"),

    BUSINESS_NAME("custom:business_name");

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
