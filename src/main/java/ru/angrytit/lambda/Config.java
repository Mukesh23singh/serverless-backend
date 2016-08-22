package ru.angrytit.lambda;

import com.amazonaws.regions.Regions;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public enum Config {
    USER_POOL_MANUFACTURER_ID("us-east-1_eqGpHIvPQ"),
    APP_CLIENT_ID("4gilggfh4ttv2rm3ca959i68gi"),
    REGION(Regions.US_EAST_1.getName());

    private final String value;

    private Config(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
