package com.analytics.dashboard.model;

import lombok.Data;
import java.util.List;

@Data
public class PredictionRequest {
    private List<Double> historicalValues;
    private int predictionSteps;
}
