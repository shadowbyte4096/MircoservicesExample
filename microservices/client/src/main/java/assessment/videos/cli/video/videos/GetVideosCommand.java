package assessment.videos.cli.video.videos;

import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-videos", description="Gets all the videos", mixinStandardHelpOptions = true)
public class GetVideosCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Override
	public void run() {
		Iterable<Video> videos = client.ListVideos();
		for (Video video : videos) {
			System.out.println(video);
		}
	}

}
