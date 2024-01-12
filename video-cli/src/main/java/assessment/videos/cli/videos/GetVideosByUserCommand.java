package assessment.videos.cli.videos;

import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-videos-by-user", description="Gets all the videos authord by a user", mixinStandardHelpOptions = true)
public class GetVideosByUserCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long userId;

	@Override
	public void run() {
		Iterable<Video> videos = client.ListByUser(userId);
		for (Video video : videos) {
			System.out.println(video);
		}
	}

}
