package ua.kpi.fict.routesearch.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ua.kpi.fict.routesearch.entity.Point;
import ua.kpi.fict.routesearch.entity.RouteSearchResult;
import ua.kpi.fict.routesearch.rest.request.PointRequest;
import ua.kpi.fict.routesearch.rest.response.RouteSearchResponse;

@Mapper
public interface PointMapper {

    List<Point> toPointList(List<PointRequest> pointRequests);

    @Mapping(target = "route", source = "points")
    RouteSearchResponse toRouteSearchResponse(RouteSearchResult routeSearchResult);
}
