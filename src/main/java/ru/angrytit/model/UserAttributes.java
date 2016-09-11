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

    TYPE("custom:type"),

    WE_ARE("custom:we_are"),
    FACEBOOK_PAGE("custom:facebook_page"),
    TWITTER_ADDRESS("custom:twitter_address"),
    INSTAGRAM_PAGE("custom:instagram_page"),

    YOU_CAN_FIND_US_HERE("custom:you_can_find_us_here"),
    WE_ARE_UNIQUE("custom:we_are_unique"),
    COMPANY_STORY("custom:company_story"),
    FOUNDER_INFO("custom:founder_info"),

    STORE_ADDRESS("custom:store_address"),
    WEBSITE_ADDRESS("custom:website_address"),
    CALL_US_HERE("custom:call_us_here"),
    EMAIL_US_HERE("custom:email_us_here");

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
