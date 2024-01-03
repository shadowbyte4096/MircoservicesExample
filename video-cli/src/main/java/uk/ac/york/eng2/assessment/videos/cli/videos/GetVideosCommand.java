package uk.ac.york.eng2.assessment.videos.cli.videos;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import uk.ac.york.eng2.assessment.videos.cli.domain.Video;

@Command(name="get-videos", description="Gets all the videos", mixinStandardHelpOptions = true)
public class GetVideosCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Override
	public void run() {
		Iterable<Video> videos = client.list();
		for (Video video : videos) {
			System.out.println(video);
		}
	}

}
