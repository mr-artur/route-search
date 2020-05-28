package ua.kpi.fict.routesearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeneticRouteSearchInputData extends RouteSearchInputData {

    private Double mutationRate;

    private Integer tournamentSize;

    private Integer populationSize;

    private Integer populationsInEvolutionCount;

    private Boolean elitismEnabled;
}
