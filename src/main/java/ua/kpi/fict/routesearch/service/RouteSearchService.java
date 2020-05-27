package ua.kpi.fict.routesearch.service;

import java.util.List;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;

public interface RouteSearchService {

    RouteSearchResult findShortestRoute(List<Point> points);
}
