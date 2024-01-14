package assessment.videos.cli.trending.hashtags;

import assessment.videos.cli.domain.Hashtag;
import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-top-ten-hashtags", description="Gets the top ten liked hashtags", mixinStandardHelpOptions = true)
public class GetTopTenHashtagsCommand implements Runnable {

	@Inject
	private HashtagsClient client;

	@Override
	public void run() {
		Iterable<Hashtag> hashtags = client.GetTopTen();
		System.out.println(hashtags == null);
		for (Hashtag hashtag : hashtags) {
			System.out.println(hashtag);
		}
	}

}
