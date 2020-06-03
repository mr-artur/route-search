package ua.kpi.fict.routesearch.factories;

import java.util.List;

import lombok.experimental.UtilityClass;

import ua.kpi.fict.routesearch.rest.request.GeneticRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.GreedyRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.PointRequest;
import ua.kpi.fict.routesearch.rest.request.RouteSearchRequest;

@UtilityClass
public class RouteSearchRequestFactory {

    public final boolean CRITICAL_TIME_TRUE = true;
    public final boolean CRITICAL_TIME_FALSE = false;
    public final PointRequest FIRST_POINT = new PointRequest(1, 25);
    public final PointRequest SECOND_POINT = new PointRequest(250, 73);
    public final PointRequest THIRD_POINT = new PointRequest(425, 56);
    public final PointRequest FOURTH_POINT = new PointRequest(43, 43);
    public final PointRequest FIFTH_POINT = new PointRequest(56, 78);
    public final List<PointRequest> POINTS = List.of(FIRST_POINT, SECOND_POINT, THIRD_POINT, FOURTH_POINT, FIFTH_POINT);

    public final Double MUTATION_RATE = 0.01;
    public final Integer POPULATION_SIZE = 25;
    public final Integer TOURNAMENT_SIZE = 10;
    public final Integer POPULATIONS_IN_EVOLUTION_COUNT = 50;
    public final Boolean ELITISM_ENABLED = true;
    public final Integer ELITE_POINTS_COUNT = 2;
    public final Integer INCORRECT_ELITE_POINTS_COUNT = 0;

    public RouteSearchRequest routeSearchRequestWithCriticalTime() {
        return RouteSearchRequest.builder()
            .timeCritical(CRITICAL_TIME_TRUE)
            .points(POINTS)
            .build();
    }

    public RouteSearchRequest routeSearchRequestWithNotCriticalTime() {
        return RouteSearchRequest.builder()
            .timeCritical(CRITICAL_TIME_FALSE)
            .points(POINTS)
            .build();
    }

    public RouteSearchRequest routeSearchRequestWithoutPoints() {
        return RouteSearchRequest.builder().build();
    }

    public GeneticRouteSearchRequest geneticRouteSearchRequestWithoutGeneticProperties() {
        return GeneticRouteSearchRequest.builder()
            .points(POINTS)
            .build();
    }

    public GeneticRouteSearchRequest geneticRouteSearchRequestWithGeneticProperties() {
        return GeneticRouteSearchRequest.builder()
            .points(POINTS)
            .mutationRate(MUTATION_RATE)
            .populationSize(POPULATION_SIZE)
            .tournamentSize(TOURNAMENT_SIZE)
            .populationsInEvolutionCount(POPULATIONS_IN_EVOLUTION_COUNT)
            .elitismEnabled(ELITISM_ENABLED)
            .elitePointsCount(ELITE_POINTS_COUNT)
            .build();
    }

    public GeneticRouteSearchRequest geneticRouteSearchRequestWithoutPoints() {
        return GeneticRouteSearchRequest.builder().build();
    }

    public GeneticRouteSearchRequest geneticRouteSearchRequestWithNotPositiveElitePointsCount() {
        return GeneticRouteSearchRequest.builder()
            .points(POINTS)
            .elitePointsCount(INCORRECT_ELITE_POINTS_COUNT)
            .build();
    }

    public GreedyRouteSearchRequest greedyRouteSearchRequest() {
        return GreedyRouteSearchRequest.builder()
            .points(POINTS)
            .build();
    }

    public GreedyRouteSearchRequest greedyRouteSearchRequestWithoutPoints() {
        return GreedyRouteSearchRequest.builder().build();
    }
}
