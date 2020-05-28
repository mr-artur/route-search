package ua.kpi.fict.routesearch.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.kpi.fict.routesearch.entity.GeneticRouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchInputData;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;
import ua.kpi.fict.routesearch.rest.request.GeneticRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.GreedyRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.RouteSearchRequest;
import ua.kpi.fict.routesearch.rest.response.RouteSearchResponse;

@Mapper
public interface RouteSearchDataMapper {

    RouteSearchInputData toRouteSearchInputData(RouteSearchRequest request);

    RouteSearchInputData toRouteSearchInputData(GreedyRouteSearchRequest request);

    GeneticRouteSearchInputData toGeneticRouteSearchInputData(GeneticRouteSearchRequest request);

    @Mapping(target = "route", source = "route.points")
    @Mapping(target = "distance", source = "route.distance")
    RouteSearchResponse toRouteSearchResponse(RouteSearchResult routeSearchResult);
}
