package uk.ac.york.eng2.assessment.videos.cli.videos;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.assessment.videos.cli.dto.VideoDTO;

@Command(name="update-video", description="Updates a video", mixinStandardHelpOptions = true)
public class UpdateVideoCommand implements Runnable {

	@Parameters(index="0")
	private Long id;

	@Option(names = {"-t", "--title"}, description="Title of the video")
	private String title;

	@Option(names = {"-y", "--year"}, description="Year the video was published")
	private Integer year;

	@Inject
	private VideosClient client;

	@Override
	public void run() {
		VideoDTO videoDetails = new VideoDTO();
		if (title != null) {
			videoDetails.setTitle(title);
		}
		if (year != null) {
			videoDetails.setYear(year);
		}
		
		HttpResponse<Void> response = client.updateVideo(id, videoDetails);
		System.out.println("Server responded with: " + response.getStatus());
	}

	
}
