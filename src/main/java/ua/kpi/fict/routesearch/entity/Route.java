package ua.kpi.fict.routesearch.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    private List<Point> points = new ArrayList<>();

    private Double fitness;

    private Integer distance;
}
