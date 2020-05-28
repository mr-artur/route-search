package ua.kpi.fict.routesearch.service;

import ua.kpi.fict.routesearch.entity.Point;

public interface PointService {

    double calculateDistance(Point firstPoint, Point secondPoint);
}
