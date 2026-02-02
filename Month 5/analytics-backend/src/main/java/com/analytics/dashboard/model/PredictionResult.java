package com.analytics.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResult {
    private List<Double> predictedValues;
    private double confidenceScore;
}
