package ua.kpi.fict.routesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:values.properties")
public class RouteSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouteSearchApplication.class, args);
    }
}
