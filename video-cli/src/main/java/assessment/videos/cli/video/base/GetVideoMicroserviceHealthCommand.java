package assessment.videos.cli.video.base;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-video-microservice-health", description="Gets the health of the Video Microservice", mixinStandardHelpOptions = true)
public class GetVideoMicroserviceHealthCommand implements Runnable {

	@Inject
	private BaseClient client;

	@Override
	public void run() {
		HttpResponse<Void> response = client.GetVideoMicroserviceHealth();
		System.out.println("Server responded with: " + response.getStatus());
	}

}
