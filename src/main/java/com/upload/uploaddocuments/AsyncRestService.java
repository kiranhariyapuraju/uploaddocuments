package com.upload.uploaddocuments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AsyncRestService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AsyncRestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void makeAsyncPostCall(String url, Object requestBody) {
        WebClient webClient = webClientBuilder.build();
        Mono<Void> responseMono = webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Void.class);

        responseMono.subscribe(); // Initiates the request asynchronously
    }
}



