package ru.angrytit.lambda;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Mikhail Tyamin <a href="mailto:mikhail.tiamine@gmail.com>mikhail.tiamine@gmail.com</a>
 */
public abstract class AbstractHandler {

    private final ApplicationContext applicationContext;

    public AbstractHandler() {
        this.applicationContext = new AnnotationConfigApplicationContext(ru.angrytit.config.Config.class);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
