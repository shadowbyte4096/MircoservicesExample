package assessment.videos.cli.video.base;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8080`}")
public interface BaseClient {

	@Get("/VideoMicroservice/")
	HttpResponse<Void> GetVideoMicroserviceHealth();
}
