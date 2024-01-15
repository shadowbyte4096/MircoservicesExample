package assessment.videos.cli.subscription.base;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8082`}")
public interface BaseClient {
	@Get("/SubscriptionMicroservice/")
	HttpResponse<Void> GetSubscriptionMicroserviceHealth();
}
