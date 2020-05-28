package ua.kpi.fict.routesearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteSearchResult {

    private Route route;

    private Long millisecondsTaken;
}
