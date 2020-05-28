package ua.kpi.fict.routesearch.rest.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteSearchResponse {

    @ApiModelProperty(notes = "List of points in a route", required = true)
    private List<PointResponse> route;

    @ApiModelProperty(notes = "Computational time", required = true)
    private Long millisecondsTaken;

    @ApiModelProperty(notes = "Distance of a found route", required = true)
    private Integer distance;
}
