package ua.kpi.fict.routesearch.rest;

import static ua.kpi.fict.routesearch.Constants.API_BASE;
import static ua.kpi.fict.routesearch.Constants.FIND_ROUTE_BASE;
import static ua.kpi.fict.routesearch.Constants.GENETIC_ALGORITHM_BASE;
import static ua.kpi.fict.routesearch.Constants.GREEDY_ALGORITHM_BASE;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;
import ua.kpi.fict.routesearch.rest.mapper.RouteSearchDataMapper;
import ua.kpi.fict.routesearch.rest.request.GeneticRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.GreedyRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.RouteSearchRequest;
import ua.kpi.fict.routesearch.rest.response.RouteSearchResponse;
import ua.kpi.fict.routesearch.service.RouteSearchService;

@Slf4j
@RestController
@RequestMapping(API_BASE + FIND_ROUTE_BASE)
@RequiredArgsConstructor
public class RouteSearchResource {

    private final RouteSearchService greedyRouteSearchService;
    private final RouteSearchService geneticRouteSearchService;
    private final RouteSearchDataMapper routeSearchDataMapper;

    @GetMapping
    public ResponseEntity<RouteSearchResponse> findShortestRoute(
        @Valid @RequestBody RouteSearchRequest request) {
        log.info("Request to find a shortest route : {}", request);
        RouteSearchInputData inputData = routeSearchDataMapper.toRouteSearchInputData(request);
        RouteSearchResult routeSearchResult = findShortestRoute(request, inputData);
        return ResponseEntity.ok(routeSearchDataMapper.toRouteSearchResponse(routeSearchResult));
    }

    private RouteSearchResult findShortestRoute(RouteSearchRequest request, RouteSearchInputData inputData) {
        if (request.isTimeCritical()) {
            return greedyRouteSearchService.findShortestRoute(inputData);
        } else {
            return geneticRouteSearchService.findShortestRoute(inputData);
        }
    }

    @GetMapping(GENETIC_ALGORITHM_BASE)
    public ResponseEntity<RouteSearchResponse> findShortestRouteViaGeneticAlgorithm(
        @Valid @RequestBody GeneticRouteSearchRequest request) {
        log.info("Request to find a shortest route via a genetic algorithm : {}", request);
        RouteSearchInputData inputData = routeSearchDataMapper.toGeneticRouteSearchInputData(request);
        RouteSearchResult routeSearchResult = geneticRouteSearchService.findShortestRoute(inputData);
        return ResponseEntity.ok(routeSearchDataMapper.toRouteSearchResponse(routeSearchResult));
    }

    @GetMapping(GREEDY_ALGORITHM_BASE)
    public ResponseEntity<RouteSearchResponse> findShortestRouteViaGreedyAlgorithm(
        @Valid @RequestBody GreedyRouteSearchRequest request) {
        log.info("Request to find a shortest route via a greedy algorithm : {}", request);
        RouteSearchInputData inputData = routeSearchDataMapper.toRouteSearchInputData(request);
        RouteSearchResult routeSearchResult = greedyRouteSearchService.findShortestRoute(inputData);
        return ResponseEntity.ok(routeSearchDataMapper.toRouteSearchResponse(routeSearchResult));
    }
}
