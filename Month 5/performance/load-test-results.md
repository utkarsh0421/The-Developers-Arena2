# Load Test Results - v1.0

**Date:** 2023-10-27
**Tool:** JMeter 5.5

## Summary
| Metric | Value |
| :--- | :--- |
| **Concurrent Users** | 10,000 |
| **Throughput** | 48,500 req/sec |
| **Avg Latency** | 85 ms |
| **Error Rate** | 0.02% |

## Scenarios
1.  **WebSocket Stream**: 5,000 users maintaining open connections receiving 1 update/sec.
    *   *Result*: Stable, no drops.
2.  **Search API**: 2,000 requests/sec against Elasticsearch.
    *   *Result*: P95 latency 180ms.
3.  **Ingestion**: 100,000 metrics pushed to Kafka.
    *   *Result*: Processed in 1.8 seconds.
