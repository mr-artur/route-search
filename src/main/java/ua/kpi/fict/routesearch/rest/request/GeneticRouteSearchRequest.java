package ua.kpi.fict.routesearch.rest.request;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(
        notes = "List of points in a 2D coordinate system",
        example = "[{\"x\":1, \"y\":2}, {\"x\":2, \"y\":3}]",
        required = true
    )
    private List<PointRequest> points;

    @ApiModelProperty(notes = "Chance of a child to mutate in a genetic algorithm", example = "0.015")
    private Double mutationRate;

    @ApiModelProperty(
        notes = "Quantity of parents participating in a tournament in a genetic algorithm",
        example = "5"
    )
    private Integer tournamentSize;

    @ApiModelProperty(notes = "Size of a population in a genetic algorithm", example = "200")
    private Integer populationSize;

    @ApiModelProperty(notes = "Quantity of populations in an evolution in a genetic algorithm", example = "200")
    private Integer populationsInEvolutionCount;

    @ApiModelProperty(notes = "Is an elitism enabled in a genetic algorithm", example = "true")
    private Boolean elitismEnabled;

    @Positive
    @ApiModelProperty(notes = "Quantity of elite points in every population in a genetic algorithm", example = "1")
    private Integer elitePointsCount;
}
