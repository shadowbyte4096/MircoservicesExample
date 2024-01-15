package assessment.videos.cli.video.videos;

import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-video", description="Adds a video", mixinStandardHelpOptions = true)
public class AddVideoCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private Long userId;
	
	@Parameters(index="1")
	private String title;

	@Override
	public void run() {
		VideoDTO dto = new VideoDTO();
		dto.setTitle(title);

		HttpResponse<String> response = client.AddVideo(userId, dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
