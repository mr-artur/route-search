package ua.kpi.fict.routesearch.service;

import java.util.List;

import ua.kpi.fict.routesearch.entity.Route;

public interface RouteService {

    Route findFittest(List<Route> routes);

    List<Route> findSeveralFittest(List<Route> routes, int quantity);

    double getFitness(Route route);

    int calculateDistance(Route route);
}
