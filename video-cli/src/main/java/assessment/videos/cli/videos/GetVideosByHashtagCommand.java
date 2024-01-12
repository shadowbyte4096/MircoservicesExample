package assessment.videos.cli.videos;

import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-videos-by-hashtag", description="Gets all the videos tagged with a hashtag", mixinStandardHelpOptions = true)
public class GetVideosByHashtagCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long hashtagId;

	@Override
	public void run() {
		Iterable<Video> videos = client.ListByHashtag(hashtagId);
		for (Video video : videos) {
			System.out.println(video);
		}
	}

}
