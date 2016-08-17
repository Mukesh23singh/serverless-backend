package ru.angrytit.lambda;

import com.amazonaws.regions.Regions;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public enum Config {
    USER_POOL_ID("us-east-1_mIlezNwN4"),
    APP_CLIENT_ID("1tqk0aq1j143kod20lmekbb1rq"),
    REGION(Regions.US_EAST_1.getName());

    private final String value;

    private Config(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
