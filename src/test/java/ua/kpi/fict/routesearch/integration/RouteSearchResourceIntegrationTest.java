package ua.kpi.fict.routesearch.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ua.kpi.fict.routesearch.Constants.API_BASE;
import static ua.kpi.fict.routesearch.Constants.FIND_ROUTE_BASE;
import static ua.kpi.fict.routesearch.Constants.GENETIC_ALGORITHM_BASE;
import static ua.kpi.fict.routesearch.Constants.GREEDY_ALGORITHM_BASE;
import static ua.kpi.fict.routesearch.TestConstants.buildJsonPathToDistance;
import static ua.kpi.fict.routesearch.TestConstants.buildJsonPathToMillisecondsTaken;
import static ua.kpi.fict.routesearch.TestConstants.buildJsonPathToRoute;
import static ua.kpi.fict.routesearch.TestConstants.buildJsonPathToPointsInRouteQuantity;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.kpi.fict.routesearch.factories.RouteSearchRequestFactory;
import ua.kpi.fict.routesearch.rest.request.GeneticRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.GreedyRouteSearchRequest;
import ua.kpi.fict.routesearch.rest.request.RouteSearchRequest;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class RouteSearchResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findShortestRoute_shouldReturnRouteSearchResponse_whenPointsListIsCorrectAndTimeCriticalIsFalse()
        throws Exception {
        RouteSearchRequest request = RouteSearchRequestFactory.routeSearchRequestWithCriticalTime();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToRoute()).exists())
            .andExpect(jsonPath(buildJsonPathToPointsInRouteQuantity()).value(request.getPoints().size()))
            .andExpect(jsonPath(buildJsonPathToDistance()).isNumber())
            .andExpect(jsonPath(buildJsonPathToMillisecondsTaken()).isNumber());
    }

    @Test
    void findShortestRoute_shouldReturnRouteSearchResponse_whenPointsListIsCorrectAndTimeCriticalIsTrue()
        throws Exception {
        RouteSearchRequest request = RouteSearchRequestFactory.routeSearchRequestWithNotCriticalTime();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToRoute()).exists())
            .andExpect(jsonPath(buildJsonPathToPointsInRouteQuantity()).value(request.getPoints().size()))
            .andExpect(jsonPath(buildJsonPathToDistance()).isNumber())
            .andExpect(jsonPath(buildJsonPathToMillisecondsTaken()).isNumber());
    }

    @Test
    void findShortestRoute_shouldRespondWithBadRequestStatus_whenPointsListIsNull() throws Exception {
        RouteSearchRequest request = RouteSearchRequestFactory.routeSearchRequestWithoutPoints();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findShortestRouteViaGeneticAlgorithm_shouldReturnRouteSearchResponse_whenRequestConsistsOfPoints()
        throws Exception {
        GeneticRouteSearchRequest request = RouteSearchRequestFactory
            .geneticRouteSearchRequestWithoutGeneticProperties();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GENETIC_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToRoute()).exists())
            .andExpect(jsonPath(buildJsonPathToPointsInRouteQuantity()).value(request.getPoints().size()))
            .andExpect(jsonPath(buildJsonPathToDistance()).isNumber())
            .andExpect(jsonPath(buildJsonPathToMillisecondsTaken()).isNumber());
    }

    @Test
    void findShortestRouteViaGeneticAlgorithm_shouldReturnRouteSearchResponse_whenRequestConsistsOfPointsAndGeneticProperties()
        throws Exception {
        GeneticRouteSearchRequest request = RouteSearchRequestFactory
            .geneticRouteSearchRequestWithGeneticProperties();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GENETIC_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToRoute()).exists())
            .andExpect(jsonPath(buildJsonPathToPointsInRouteQuantity()).value(request.getPoints().size()))
            .andExpect(jsonPath(buildJsonPathToDistance()).isNumber())
            .andExpect(jsonPath(buildJsonPathToMillisecondsTaken()).isNumber());
    }

    @Test
    void findShortestRouteViaGeneticAlgorithm_shouldRespondWithBadRequestStatus_whenPointsListIsNull()
        throws Exception {
        GeneticRouteSearchRequest request = RouteSearchRequestFactory.geneticRouteSearchRequestWithoutPoints();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GENETIC_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findShortestRouteViaGeneticAlgorithm_shouldRespondWithBadRequestStatus_whenElitePointsCountIsNotPositive()
        throws Exception {
        GeneticRouteSearchRequest request = RouteSearchRequestFactory
            .geneticRouteSearchRequestWithNotPositiveElitePointsCount();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GENETIC_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findShortestRouteViaGreedyAlgorithm_shouldReturnRouteSearchResponse_whenPointsListIsCorrect()
        throws Exception {
        GreedyRouteSearchRequest request = RouteSearchRequestFactory.greedyRouteSearchRequest();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GREEDY_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToRoute()).exists())
            .andExpect(jsonPath(buildJsonPathToPointsInRouteQuantity()).value(request.getPoints().size()))
            .andExpect(jsonPath(buildJsonPathToDistance()).isNumber())
            .andExpect(jsonPath(buildJsonPathToMillisecondsTaken()).isNumber());
    }

    @Test
    void findShortestRouteViaGreedyAlgorithm_shouldRespondWithBadRequestStatus_whenPointsListIsNull()
        throws Exception {
        GreedyRouteSearchRequest request = RouteSearchRequestFactory.greedyRouteSearchRequestWithoutPoints();

        mockMvc.perform(get(API_BASE + FIND_ROUTE_BASE + GREEDY_ALGORITHM_BASE)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }
}
