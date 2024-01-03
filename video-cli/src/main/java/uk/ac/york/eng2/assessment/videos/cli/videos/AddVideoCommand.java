package uk.ac.york.eng2.assessment.videos.cli.videos;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.assessment.videos.cli.dto.VideoDTO;

@Command(name="add-video", description="Adds a video", mixinStandardHelpOptions = true)
public class AddVideoCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private String title;

	@Override
	public void run() {
		VideoDTO dto = new VideoDTO();
		dto.setTitle(title);

		HttpResponse<Void> response = client.add(dto);
		System.out.println("Server responded with: " + response.getStatus());
	}

}
