package com.example.erp.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "erp")
public class ERPProperties {
    private String environment = "dev";
    private Mongo mongo = new Mongo();
    private Postgres postgres = new Postgres();
    private Cache cache = new Cache();

    // getters/setters

    public static class Mongo {
        private String uri;
        public String getUri() { return uri; }
        public void setUri(String uri) { this.uri = uri; }
    }

    public static class Postgres {
        private String url;
        private String username;
        private String password;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class Cache {
        private String host;
        private int port;
        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
    }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public Mongo getMongo() { return mongo; }
    public void setMongo(Mongo mongo) { this.mongo = mongo; }
    public Postgres getPostgres() { return postgres; }
    public void setPostgres(Postgres postgres) { this.postgres = postgres; }
    public Cache getCache() { return cache; }
    public void setCache(Cache cache) { this.cache = cache; }
}

