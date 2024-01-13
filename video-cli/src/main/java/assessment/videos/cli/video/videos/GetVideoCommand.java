package assessment.videos.cli.video.videos;

import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-video", description="Gets a specific video", mixinStandardHelpOptions = true)
public class GetVideoCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		VideoDTO video = client.GetVideo(id);
		if (video == null) {
			System.err.println("Video not found!");
			System.exit(1);
		} else {
			System.out.println(video);
		}
	}

	
}
