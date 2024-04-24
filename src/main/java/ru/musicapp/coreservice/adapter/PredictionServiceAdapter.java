package ru.musicapp.coreservice.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.*;

@Component
public class PredictionServiceAdapter {

    @Autowired
    @Qualifier("predictionServiceClient")
    private RestClient restClient;

    public Set<UUID> generateSimilarUsers(UUID userId) {
        UUID[] userIds = restClient.post()
                .uri("api/v1/private/user-stat/{userId}", userId)
                .retrieve()
                .body(UUID[].class);

        if (userIds == null || userIds.length == 0) {
            return Collections.emptySet();
        }

        return new HashSet<>(List.of(userIds));
    }

}
