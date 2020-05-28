package ua.kpi.fict.routesearch.service;

import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;

public interface RouteSearchService {

    RouteSearchResult findShortestRoute(RouteSearchInputData inputData);
}
