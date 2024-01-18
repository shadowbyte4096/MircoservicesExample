package assessment.base;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("/")
public interface BaseClient {
	
	@Get("/TrendingHashtagMicroservice/")
	HttpResponse<Void> GetTrendingHashtagMicroserviceHealth();

}
