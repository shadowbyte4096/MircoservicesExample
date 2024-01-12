package assessment.videos.cli.base;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-trending-hashtag-microservice-health", description="Gets the health of the Trending Hashtag Microservice", mixinStandardHelpOptions = true)
public class GetTrendingHashtagMicroserviceHealth implements Runnable {

	@Inject
	private BaseClient client;

	@Override
	public void run() {
		HttpResponse<Void> response = client.GetTrendingHashtagMicroserviceHealth();
		System.out.println("Server responded with: " + response.getStatus());
	}

}
