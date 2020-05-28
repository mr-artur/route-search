package ua.kpi.fict.routesearch.rest.request;

import io.swagger.annotations.ApiModelProperty;

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
public class RouteSearchRequest {

    @NotNull
    @ApiModelProperty(
        notes = "List of points in a 2D coordinate system",
        example = "[{\"x\":1, \"y\":2}, {\"x\":2, \"y\":3}]",
        required = true
    )
    private List<PointRequest> points;

    @ApiModelProperty(notes = "Is a time critical for this request", example = "true")
    private boolean timeCritical;
}
