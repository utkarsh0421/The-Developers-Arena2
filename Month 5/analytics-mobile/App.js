import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, ScrollView, RefreshControl } from 'react-native';
import { LineChart } from 'react-native-chart-kit';
import { WebSocket } from 'react-native-websocket';

const AnalyticsDashboard = () => {
  const [metrics, setMetrics] = useState([]);
  const [refreshing, setRefreshing] = useState(false);

  const chartConfig = {
    backgroundGradientFrom: "#1E2923",
    backgroundGradientFromOpacity: 0,
    backgroundGradientTo: "#08130D",
    backgroundGradientToOpacity: 0.5,
    color: (opacity = 1) => `rgba(26, 255, 146, ${opacity})`,
    strokeWidth: 2,
    barPercentage: 0.5,
    useShadowColorFromDataset: false
  };

  useEffect(() => {
    // Fetch initial data
    fetchAnalyticsData();

    // Set up WebSocket for real-time updates
    const ws = new WebSocket('ws://your-server.com/ws/analytics');

    ws.onopen = () => {
      console.log('WebSocket connected');
    };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setMetrics(prev => [data, ...prev.slice(0, 49)]); // Keep last 50 metrics
    };

    return () => ws.close();
  }, []);

  const fetchAnalyticsData = async () => {
    try {
      const response = await fetch('https://your-server.com/api/analytics/metrics');
      const data = await response.json();
      setMetrics(data);
    } catch (error) {
      console.error('Error fetching analytics:', error);
    }
  };

  const onRefresh = async () => {
    setRefreshing(true);
    await fetchAnalyticsData();
    setRefreshing(false);
  };

  return (
    <ScrollView 
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
      }
    >
      <View style={styles.container}>
        <Text style={styles.title}>Real-time Analytics Dashboard</Text>
        
        {metrics.length > 0 && (
          <>
            <LineChart
              data={{
                labels: metrics.map((_, i) => i.toString()),
                datasets: [{
                  data: metrics.map(m => m.value)
                }]
              }}
              width={350}
              height={200}
              chartConfig={chartConfig}
            />
            
            <View style={styles.statsContainer}>
              <Text style={styles.stat}>Current: {metrics[0]?.value || 0}</Text>
              <Text style={styles.stat}>Average: {
                (metrics.reduce((sum, m) => sum + m.value, 0) / metrics.length).toFixed(2)
              }</Text>
              <Text style={styles.stat}>Max: {
                Math.max(...metrics.map(m => m.value))
              }</Text>
            </View>
          </>
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  statsContainer: {
    marginTop: 20,
    padding: 10,
    backgroundColor: '#f5f5f5',
    borderRadius: 10,
  },
  stat: {
    fontSize: 16,
    marginVertical: 5,
  }
});

export default AnalyticsDashboard;
