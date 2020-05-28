package ua.kpi.fict.routesearch.rest.response;

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

    private List<PointResponse> route;

    private Long millisecondsTaken;

    private Integer distance;
}
