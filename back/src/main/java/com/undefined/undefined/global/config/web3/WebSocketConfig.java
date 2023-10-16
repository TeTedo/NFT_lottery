package com.undefined.undefined.global.config.web3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class WebSocketConfig {

    @Value("${uri.websocket-uri}")
    private String WEBSOCKET_URI;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Bean
    public WebSocketService webSocketService() {
        WebSocketService webSocketService = new WebSocketService(WEBSOCKET_URI, false);
        connectWebSocketService(webSocketService);
        return webSocketService;
    }

    private void connectWebSocketService(WebSocketService webSocketService) {
        Consumer<String> onMessage = message -> {
            System.out.println(message);
        };

        Consumer<Throwable> onError = throwable -> {
            log.error("WebSocket 오류 발생: ", throwable);
        };

        Runnable onClose = () -> {
            log.warn("WebSocket 연결이 종료됨. 재연결 시도...");
            // 기존 스레드는 close 되면서 종료됨. 별도의 스레드에서 재연결
            executorService.submit(() -> connectWebSocketService(webSocketService));
        };

        try {
            webSocketService.connect(onMessage, onError, onClose);
        } catch (Exception e) {
            log.error("WebSocket 연결 중 오류 발생", e);
        }
    }
}
