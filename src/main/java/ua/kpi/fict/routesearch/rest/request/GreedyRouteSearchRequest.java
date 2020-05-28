package ua.kpi.fict.routesearch.rest.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GreedyRouteSearchRequest {

    @NotNull
    private List<PointRequest> points;
}
