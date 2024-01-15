package assessment.videos.cli.video.hashtags;

import assessment.videos.cli.domain.Hashtag;
import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-hashtags", description="Gets all the hashtags", mixinStandardHelpOptions = true)
public class GetHashtagsCommand implements Runnable {

	@Inject
	private HashtagsClient client;

	@Override
	public void run() {
		Iterable<Hashtag> hashtags = client.ListHashtags();
		for (Hashtag hashtag : hashtags) {
			System.out.println(hashtag);
		}
	}

}
