package ua.kpi.fict.routesearch;

import static ua.kpi.fict.routesearch.Constants.VALUE_PROPERTIES_CLASSPATH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(VALUE_PROPERTIES_CLASSPATH)
public class RouteSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteSearchApplication.class, args);
    }
}
