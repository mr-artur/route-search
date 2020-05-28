package ua.kpi.fict.routesearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.Route;
import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.service.TimingRouteSearchService;

@Service
public class GreedyRouteSearchService extends TimingRouteSearchService {

    private static final Random random = new Random();

    @Override
    protected Route findRoute(RouteSearchInputData inputData) {
        List<Point> copiedPoints = new ArrayList<>(inputData.getPoints());
        List<Point> resultPoints = new ArrayList<>();

        Point currentPoint = chooseFirstPointAndMoveItToResult(copiedPoints, resultPoints);
        addRemainingPointsToResult(copiedPoints, resultPoints, currentPoint);
        return Route.builder().points(resultPoints).build();
    }

    private Point chooseFirstPointAndMoveItToResult(List<Point> copiedPoints, List<Point> resultPoints) {
        Point currentPoint = getRandomPoint(copiedPoints);
        resultPoints.add(currentPoint);
        copiedPoints.remove(currentPoint);
        return currentPoint;
    }

    private Point getRandomPoint(List<Point> copiedPoints) {
        return copiedPoints.get(random.nextInt(copiedPoints.size()));
    }

    private void addRemainingPointsToResult(List<Point> copiedPoints, List<Point> resultPoints, Point currentPoint) {
        while (!copiedPoints.isEmpty()) {
            Point nearestPoint = findNearestPointInList(currentPoint, copiedPoints);
            resultPoints.add(nearestPoint);
            currentPoint = nearestPoint;
            copiedPoints.remove(nearestPoint);
        }
    }

    private Point findNearestPointInList(Point currentPoint, List<Point> points) {
        Point nearestPoint = points.get(0);
        double nearestDistance = calculateDistance(currentPoint, nearestPoint);
        for (int i = 1; i < points.size(); i++) {
            if (calculateDistance(currentPoint, points.get(i)) < nearestDistance) {
                nearestDistance = calculateDistance(currentPoint, points.get(i));
                nearestPoint = points.get(i);
            }
        }
        return nearestPoint;
    }

    private double calculateDistance(Point firstPoint, Point secondPoint) {
        int xDistance = Math.abs(firstPoint.getX() - secondPoint.getX());
        int yDistance = Math.abs(firstPoint.getY() - secondPoint.getY());
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }
}
