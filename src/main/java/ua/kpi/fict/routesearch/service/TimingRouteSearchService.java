package ua.kpi.fict.routesearch.service;

import java.time.Clock;
import java.util.List;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;

public abstract class TimingRouteSearchService implements RouteSearchService {

    @Override
    public RouteSearchResult findShortestRoute(RouteSearchInputData inputData) {
        Clock clock = Clock.systemDefaultZone();
        RouteSearchResult result = new RouteSearchResult();

        long millisecondsAtStart = clock.millis();
        List<Point> route = findRoute(inputData);
        long millisecondsAtEnd = clock.millis();

        result.setPoints(route);
        result.setMillisecondsTaken(millisecondsAtEnd - millisecondsAtStart);
        return result;
    }

    protected abstract List<Point> findRoute(RouteSearchInputData inputData);
}
