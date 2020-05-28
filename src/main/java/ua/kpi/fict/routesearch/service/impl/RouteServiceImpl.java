package ua.kpi.fict.routesearch.service.impl;

import static java.util.Objects.isNull;

import java.util.Iterator;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.Route;
import ua.kpi.fict.routesearch.service.PointService;
import ua.kpi.fict.routesearch.service.RouteService;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final PointService pointService;

    @Override
    public Route findFittest(List<Route> routes) {
        Route fittestRoute = routes.get(0);
        for (int i = 1; i < routes.size(); i++) {
            if (getFitness(fittestRoute) <= getFitness(routes.get(i))) {
                fittestRoute = routes.get(i);
            }
        }
        return fittestRoute;
    }

    @Override
    public double getFitness(Route route) {
        if (isNull(route.getFitness())) {
            route.setFitness(1 / (double) calculateDistance(route));
        }
        return route.getFitness();
    }

    @Override
    public int calculateDistance(Route route) {
        if (isNull(route.getDistance())) {
            int distance = 0;
            Iterator<Point> iterator = route.getPoints().iterator();
            Point from = iterator.next();

            while (iterator.hasNext()) {
                Point to = iterator.next();
                distance += pointService.calculateDistance(from, to);
                from = to;
            }
            route.setDistance(distance);
        }
        return route.getDistance();
    }
}
