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
        while (true) {
            try {
                webSocketService.connect();
                break;
            } catch (Exception e) {  // 모든 예외를 처리하도록 변경
                log.error("2초 후에 재연결 시도", e);
                try {
                    Thread.sleep(2000); // 2초 후에 재연결 시도
                } catch (InterruptedException ex) {
                    log.error("연결 실패", ex);
                    break;
                }
            }
        }
    }
}
