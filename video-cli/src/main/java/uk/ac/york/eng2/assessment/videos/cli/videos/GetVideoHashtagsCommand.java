package uk.ac.york.eng2.assessment.videos.cli.videos;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.assessment.videos.cli.domain.Hashtag;
import uk.ac.york.eng2.assessment.videos.cli.domain.User;
import uk.ac.york.eng2.assessment.videos.cli.videos.VideosClient;

@Command(name="get-video-hashtags", description="Gets the hashtags of a specific video", mixinStandardHelpOptions = true)
public class GetVideoHashtagsCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<Hashtag> hashtags = client.getHashtags(id);
		if (hashtags == null) {
			System.out.println("Video does not exist");
		}
		for (Hashtag hashtag : hashtags) {
			System.out.println(hashtag);
		}
	}
	
}
