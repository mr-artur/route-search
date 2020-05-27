package ua.kpi.fict.routesearch.service.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.Population;
import ua.kpi.fict.routesearch.entity.Route;
import ua.kpi.fict.routesearch.service.TimingRouteSearchService;

@Service
public class GeneticRouteSearchService extends TimingRouteSearchService {

    private static final double MUTATION_RATE = 0.015;
    private static final int TOURNAMENT_SIZE = 5;
    private static final boolean ELITISM_ENABLED = true;
    private static final int POPULATION_SIZE = 200;
    private static final int POPULATIONS_IN_EVOLUTION = 200;

    @Override
    protected List<Point> findRoute(List<Point> points) {
        Population population = new Population();
        generateRandomRoutes(points, population);
        population = performEvolution(population);
        return findFittest(population).getPoints();
    }

    private void generateRandomRoutes(List<Point> points, Population population) {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Route route = Route.builder().points(new ArrayList<>(points)).build();
            Collections.shuffle(route.getPoints());
            population.getRoutes().add(route);
        }
    }

    private Population performEvolution(Population population) {
        for (int i = 0; i < POPULATIONS_IN_EVOLUTION; i++) {
            population = evolve(population);
        }
        return population;
    }

    private Population evolve(Population population) {
        Population newPopulation = new Population(new ArrayList<>(population.getRoutes().size()));
        int firstChangeableElementIndex = 0;
        if (ELITISM_ENABLED) {
            newPopulation.getRoutes().add(findFittest(population));
            firstChangeableElementIndex = 1;
        }
        provideInheritanceInPopulation(population, newPopulation, firstChangeableElementIndex);
        performMutationInPopulation(newPopulation, firstChangeableElementIndex);
        return newPopulation;
    }

    private void provideInheritanceInPopulation(Population population, Population newPopulation,
        int firstChangeableElementIndex) {
        for (int i = firstChangeableElementIndex; i < population.getRoutes().size(); i++) {
            Route firstParent = selectByTournament(population);
            Route secondParent = selectByTournament(population);
            Route child = crossover(firstParent, secondParent);
            newPopulation.getRoutes().add(i, child);
        }
    }

    private void performMutationInPopulation(Population newPopulation, int firstChangeableElementIndex) {
        for (int i = firstChangeableElementIndex; i < newPopulation.getRoutes().size(); i++) {
            mutate(newPopulation.getRoutes().get(i));
        }
    }

    private Route findFittest(Population population) {
        List<Route> routes = population.getRoutes();
        Route fittestRoute = routes.get(0);
        for (int i = 1; i < routes.size(); i++) {
            if (getRouteFitness(fittestRoute) <= getRouteFitness(routes.get(i))) {
                fittestRoute = routes.get(i);
            }
        }
        return fittestRoute;
    }

    private double getRouteFitness(Route route) {
        if (isNull(route.getFitness())) {
            route.setFitness(1 / (double) calculateDistance(route));
        }
        return route.getFitness();
    }

    private int calculateDistance(Route route) {
        if (isNull(route.getDistance())) {
            int distance = 0;
            Iterator<Point> iterator = route.getPoints().iterator();
            Point from = iterator.next();

            while (iterator.hasNext()) {
                Point to = iterator.next();
                distance += calculateDistance(from, to);
                from = to;
            }
            route.setDistance(distance);
        }
        return route.getDistance();
    }

    private double calculateDistance(Point firstPoint, Point secondPoint) {
        int xDistance = Math.abs(firstPoint.getX() - secondPoint.getX());
        int yDistance = Math.abs(firstPoint.getY() - secondPoint.getY());
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    private Route crossover(Route firstParent, Route secondParent) {
        Route child = initializeRoute(firstParent.getPoints().size());
        addPointsFromFirstParent(firstParent, child);
        addPointsFromSecondParent(secondParent, child);
        return child;
    }

    private Route initializeRoute(int size) {
        Route route = new Route();
        for (int i = 0; i < size; i++) {
            route.getPoints().add(null);
        }
        return route;
    }

    private void addPointsFromFirstParent(Route parent, Route child) {
        int startPos = (int) (Math.random() * parent.getPoints().size());
        int endPos = (int) (Math.random() * parent.getPoints().size());

        for (int i = 0; i < parent.getPoints().size(); i++) {
            if (i > startPos && i < endPos) {
                child.getPoints().set(i, parent.getPoints().get(i));
            } else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.getPoints().set(i, parent.getPoints().get(i));
                }
            }
        }
    }

    private void addPointsFromSecondParent(Route parent, Route child) {
        for (int i = 0; i < parent.getPoints().size(); i++) {
            if (!child.getPoints().contains(parent.getPoints().get(i))) {
                for (int j = 0; j < child.getPoints().size(); j++) {
                    if (child.getPoints().get(j) == null) {
                        child.getPoints().set(j, parent.getPoints().get(i));
                        break;
                    }
                }
            }
        }
    }

    private void mutate(Route route) {
        for (int i = 0; i < route.getPoints().size(); i++) {
            if (Math.random() < MUTATION_RATE) {
                int secondPointIndex = (int) (route.getPoints().size() * Math.random());

                Point firstPoint = route.getPoints().get(i);
                Point secondPoint = route.getPoints().get(secondPointIndex);

                route.getPoints().set(secondPointIndex, firstPoint);
                route.getPoints().set(i, secondPoint);
            }
        }
    }

    private Route selectByTournament(Population population) {
        Population tournament = new Population(new ArrayList<>(TOURNAMENT_SIZE));

        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int index = (int) (Math.random() * population.getRoutes().size());
            tournament.getRoutes().add(i, population.getRoutes().get(index));
        }
        return findFittest(tournament);
    }
}
