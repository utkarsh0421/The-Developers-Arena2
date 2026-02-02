package com.analytics.dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Service
public class WebSocketService implements WebSocketHandler {
    
    // Basic implementation of WebSocket handler
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
            session.receive()
                .map(msg -> session.textMessage("Echo: " + msg.getPayloadAsText()))
        );
    }
}
