package assessment.videos.cli.trending.base;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8081`}")
public interface BaseClient {
	
	@Get("/TrendingHashtagMicroservice/")
	HttpResponse<Void> GetTrendingHashtagMicroserviceHealth();
}
