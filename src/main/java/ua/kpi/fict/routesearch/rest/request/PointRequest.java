package ua.kpi.fict.routesearch.rest.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointRequest {

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(notes = "X coordinate of a point", example = "1")
    private Integer x;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(notes = "Y coordinate of a point", example = "1")
    private Integer y;
}
