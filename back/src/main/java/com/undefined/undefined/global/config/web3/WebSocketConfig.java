package com.undefined.undefined.global.config.web3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class WebSocketConfig {
    private static final String WEBSOCKET_URI = "wss://archive-en.baobab.klaytn.net/ws";

    @Bean
    public WebSocketService webSocketService() {
        WebSocketService webSocketService = new WebSocketService(WEBSOCKET_URI, false);
        connectWebSocketService(webSocketService);
        return webSocketService;
    }

    private void connectWebSocketService(WebSocketService webSocketService) {
        Consumer<String> onMessage = message -> {
            // 메시지를 받을 때 수행할 동작을 여기에 작성합니다.
        };

        Consumer<Throwable> onError = throwable -> {
            log.error("WebSocket 오류 발생: ", throwable);
        };

        Runnable onClose = () -> {
            log.warn("WebSocket 연결이 종료됨. 재연결 시도...");
            connectWebSocketService(webSocketService);
        };

        try {
            webSocketService.connect(onMessage, onError, onClose);
        } catch (Exception e) {
            log.error("WebSocket 연결 중 오류 발생", e);
        }
    }
}
