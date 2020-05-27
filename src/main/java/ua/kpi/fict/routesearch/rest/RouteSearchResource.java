package ua.kpi.fict.routesearch.rest;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;
import ua.kpi.fict.routesearch.rest.mapper.PointMapper;
import ua.kpi.fict.routesearch.rest.request.PointRequest;
import ua.kpi.fict.routesearch.rest.request.RouteSearchRequest;
import ua.kpi.fict.routesearch.rest.response.RouteSearchResponse;
import ua.kpi.fict.routesearch.service.RouteSearchService;

@Slf4j
@RestController
@RequestMapping("/find-route")
@RequiredArgsConstructor
public class RouteSearchResource {

    private final RouteSearchService greedyRouteSearchService;
    private final RouteSearchService geneticRouteSearchService;
    private final PointMapper pointMapper;

    @GetMapping
    public ResponseEntity<RouteSearchResponse> findShortestRoute(
        @Valid @RequestBody RouteSearchRequest request) {

        log.info("Request to find a shortest route : {}", request);
        List<Point> points = pointMapper.toPointList(request.getPoints());
        RouteSearchResult routeSearchResult;

        if (request.isTimeCritical()) {
            routeSearchResult = greedyRouteSearchService.findShortestRoute(points);
        } else {
            routeSearchResult = geneticRouteSearchService.findShortestRoute(points);
        }
        return ResponseEntity.ok(pointMapper.toRouteSearchResponse(routeSearchResult));
    }

    @GetMapping("/genetic")
    public ResponseEntity<RouteSearchResponse> findShortestRouteViaGeneticAlgorithm(
        @Valid @RequestBody List<PointRequest> pointRequests) {

        log.info("Request to find a shortest route by a genetic algorithm : {}", pointRequests);
        List<Point> points = pointMapper.toPointList(pointRequests);
        RouteSearchResult routeSearchResult = geneticRouteSearchService.findShortestRoute(points);

        return ResponseEntity.ok(pointMapper.toRouteSearchResponse(routeSearchResult));
    }

    @GetMapping("/greedy")
    public ResponseEntity<RouteSearchResponse> findShortestRouteViaGreedyAlgorithm(
        @Valid @RequestBody List<PointRequest> pointRequests) {

        log.info("Request to find a shortest route by a greedy algorithm : {}", pointRequests);
        List<Point> points = pointMapper.toPointList(pointRequests);
        RouteSearchResult routeSearchResult = greedyRouteSearchService.findShortestRoute(points);

        return ResponseEntity.ok(pointMapper.toRouteSearchResponse(routeSearchResult));
    }
}
