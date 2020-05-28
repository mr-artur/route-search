package ua.kpi.fict.routesearch.rest.response;

import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointResponse {

    @ApiModelProperty(notes = "X coordinate of a point", example = "1", required = true)
    private Integer x;

    @ApiModelProperty(notes = "Y coordinate of a point", example = "1", required = true)
    private Integer y;
}
