package com.undefined.undefined.global.config.web3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

@Configuration
public class Web3jConfig {


    private static final String HTTP_RPC_URI = "https://public-node-api.klaytnapi.com/v1/baobab";



    @Bean
    public Web3j web3jWebsocket(WebSocketService webSocketService) {
        return Web3j.build(webSocketService);
    }

    @Bean
    public Web3j web3jHttpRpc() {
        return Web3j.build(new HttpService(HTTP_RPC_URI));
    }


}
