package ua.kpi.fict.routesearch.service;

import java.util.List;

import ua.kpi.fict.routesearch.entity.Route;

public interface RouteService {

    public Route findFittest(List<Route> routes);

    public List<Route> findSeveralFittest(List<Route> routes, int quantity);

    public double getFitness(Route route);

    public int calculateDistance(Route route);
}
