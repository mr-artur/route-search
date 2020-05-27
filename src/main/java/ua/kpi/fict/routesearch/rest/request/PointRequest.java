package ua.kpi.fict.routesearch.rest.request;

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
    private Integer x;

    @NotNull
    @PositiveOrZero
    private Integer y;
}
