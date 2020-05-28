package ua.kpi.fict.routesearch.rest.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneticRouteSearchRequest {

    @NotNull
    private List<PointRequest> points;

    private Double mutationRate;

    private Integer tournamentSize;

    private Integer populationSize;

    private Integer populationsInEvolutionCount;

    private Boolean elitismEnabled;

    @Positive
    private Integer elitePointsCount;
}
