package ua.kpi.fict.routesearch;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_ROUTE = "route";
    public static final String PARAMETER_DISTANCE = "distance";
    public static final String PARAMETER_MILLISECONDS_TAKEN = "millisecondsTaken";
    public static final String PARAMETER_LENGTH = "length()";

    public static String buildJsonPathToRoute() {
        return JSON_ROOT + PARAMETER_ROUTE;
    }

    public static String buildJsonPathToPointsInRouteQuantity() {
        return JSON_ROOT + PARAMETER_ROUTE + "." + PARAMETER_LENGTH;
    }

    public static String buildJsonPathToDistance() {
        return JSON_ROOT + PARAMETER_DISTANCE;
    }

    public static String buildJsonPathToMillisecondsTaken() {
        return JSON_ROOT + PARAMETER_MILLISECONDS_TAKEN;
    }
}
