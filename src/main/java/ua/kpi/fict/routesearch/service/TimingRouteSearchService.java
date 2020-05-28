package ua.kpi.fict.routesearch.service;

import java.time.Clock;

import ua.kpi.fict.routesearch.entity.Route;
import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;

public abstract class TimingRouteSearchService implements RouteSearchService {

    @Override
    public RouteSearchResult findShortestRoute(RouteSearchInputData inputData) {
        Clock clock = Clock.systemDefaultZone();
        RouteSearchResult result = new RouteSearchResult();

        long millisecondsAtStart = clock.millis();
        Route route = findRoute(inputData);
        long millisecondsAtEnd = clock.millis();

        result.setRoute(route);
        result.setMillisecondsTaken(millisecondsAtEnd - millisecondsAtStart);
        return result;
    }

    protected abstract Route findRoute(RouteSearchInputData inputData);
}
