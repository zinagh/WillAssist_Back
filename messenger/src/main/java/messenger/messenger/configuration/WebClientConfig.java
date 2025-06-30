package messenger.messenger.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder  webClientBuilder(AuthorizationUtils authorizationUtils){
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            String token = authorizationUtils.addAuthorizationHeader();
                            if (token != null) {
                                return Mono.just(ClientRequest.from(clientRequest)
                                        .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                                        .build());
                            }
                            return Mono.just(clientRequest);
                        }));
    }



    @Bean
    public WebClient botClient(@Value("${chat.bot-base-url}") String baseUrl,
                               AuthorizationUtils auth) {

        // create a brand-new builder – **not** the load-balanced one
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(req -> {
                    String token = auth.addAuthorizationHeader();
                    return token == null ? Mono.just(req)
                            : Mono.just(ClientRequest.from(req)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .build());
                }))
                .baseUrl(baseUrl)
                .build();
    }


}