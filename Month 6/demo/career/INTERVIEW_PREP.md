# Technical Interview Preparation Guide

## 1. System Design Questions
### Design a Startup Platform (Like this one)
- **Requirements**: Connect startups, investors, job seekers.
- **High Level**: Microservices vs Monolith (Start with Modular Monolith).
- **Database**: MongoDB for flexible schemas (Startup Profiles), PostgreSQL for transactional data (Investments).
- **Scaling**: Caching (Redis) for profiles, Async (Kafka/RabbitMQ) for notifications.

## 2. Java / Spring Boot Questions
- **Dependency Injection**: Explain `@Autowired`, `@Component`, `@Service`.
- **Spring Boot Starters**: How they work (AutoConfiguration).
- **Security**: JWT vs Session, OAuth2 flow.
- **Concurrency**: `CompletableFuture`, Virtual Threads (Java 21).

## 3. React / Frontend Questions
- **Hooks**: `useEffect`, `useState`, Custom Hooks.
- **State Management**: Context API vs Redux/Zustand.
- **Performance**: Memoization (`useMemo`, `useCallback`), Code Splitting.

## 4. DevOps Questions
- **CI/CD**: Explain the pipeline stages (Build, Test, Scan, Deploy).
- **Kubernetes**: Pods, Deployments, Services, Ingress.
- **Docker**: Layers, Multi-stage builds.
