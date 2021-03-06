package ua.kpi.fict.routesearch.service.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ua.kpi.fict.routesearch.entity.GeneticRouteSearchInputData;
import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.Population;
import ua.kpi.fict.routesearch.entity.Route;
import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.service.RouteService;
import ua.kpi.fict.routesearch.service.TimingRouteSearchService;

@Service
@RequiredArgsConstructor
public class GeneticRouteSearchService extends TimingRouteSearchService {

    private final RouteService routeService;

    @Value("${default.mutation-rate}")
    private double defaultMutationRate;

    @Value("${default.is-elitism-enabled}")
    private boolean defaultIsElitismEnabled;

    @Value("${default.tournament-size}")
    private int defaultTournamentSize;

    @Value("${default.population-size}")
    private int defaultPopulationSize;

    @Value("${default.populations-in-evolution-count}")
    private int defaultPopulationsInEvolutionCount;

    @Value("${default.elite-points-count}")
    private int defaultElitePointsCount;

    private double mutationRate;
    private boolean isElitismEnabled;
    private int tournamentSize;
    private int populationSize;
    private int populationsInEvolutionCount;
    private int elitePointsCount;

    @Override
    protected Route findRoute(RouteSearchInputData inputData) {
        Population population = new Population();
        updateGeneticPropertiesIfNeeded(inputData);
        generateRandomRoutes(inputData.getPoints(), population);
        population = performEvolution(population);
        return routeService.findFittest(population.getRoutes());
    }

    private void updateGeneticPropertiesIfNeeded(RouteSearchInputData inputData) {
        if (inputData instanceof GeneticRouteSearchInputData) {
            setGeneticPropertiesFromInputData((GeneticRouteSearchInputData) inputData);
        } else {
            setDefaultGeneticProperties();
        }
    }

    private void setDefaultGeneticProperties() {
        mutationRate =  defaultMutationRate;
        tournamentSize = defaultTournamentSize;
        populationSize = defaultPopulationSize;
        isElitismEnabled = defaultIsElitismEnabled;
        populationsInEvolutionCount = defaultPopulationsInEvolutionCount;
        elitePointsCount = defaultElitePointsCount;
    }

    private void setGeneticPropertiesFromInputData(GeneticRouteSearchInputData inputData) {
        mutationRate = isNull(inputData.getMutationRate()) ? defaultMutationRate : inputData.getMutationRate();
        tournamentSize = isNull(inputData.getTournamentSize()) ? defaultTournamentSize : inputData.getTournamentSize();
        populationSize = isNull(inputData.getPopulationSize()) ? defaultPopulationSize : inputData.getPopulationSize();
        isElitismEnabled = isNull(inputData.getElitismEnabled())
            ? defaultIsElitismEnabled : inputData.getElitismEnabled();
        populationsInEvolutionCount = isNull(inputData.getPopulationsInEvolutionCount())
            ? defaultPopulationsInEvolutionCount : inputData.getPopulationsInEvolutionCount();
        elitePointsCount = isNull(inputData.getElitePointsCount()) || !defaultIsElitismEnabled
            ? defaultElitePointsCount : inputData.getElitePointsCount();
    }

    private void generateRandomRoutes(List<Point> points, Population population) {
        for (int i = 0; i < populationSize; i++) {
            Route route = Route.builder().points(new ArrayList<>(points)).build();
            Collections.shuffle(route.getPoints());
            population.getRoutes().add(route);
        }
    }

    private Population performEvolution(Population population) {
        for (int i = 0; i < populationsInEvolutionCount; i++) {
            population = evolve(population);
        }
        return population;
    }

    private Population evolve(Population population) {
        Population newPopulation = new Population(new ArrayList<>(population.getRoutes().size()));
        int firstChangeableElementIndex = 0;
        if (isElitismEnabled) {
            newPopulation.getRoutes().addAll(routeService.findSeveralFittest(population.getRoutes(), elitePointsCount));
            firstChangeableElementIndex = elitePointsCount;
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
            if (Math.random() < mutationRate) {
                int secondPointIndex = (int) (route.getPoints().size() * Math.random());

                Point firstPoint = route.getPoints().get(i);
                Point secondPoint = route.getPoints().get(secondPointIndex);

                route.getPoints().set(secondPointIndex, firstPoint);
                route.getPoints().set(i, secondPoint);
            }
        }
    }

    private Route selectByTournament(Population population) {
        Population tournament = new Population(new ArrayList<>(tournamentSize));

        for (int i = 0; i < tournamentSize; i++) {
            int index = (int) (Math.random() * population.getRoutes().size());
            tournament.getRoutes().add(i, population.getRoutes().get(index));
        }
        return routeService.findFittest(tournament.getRoutes());
    }
}
