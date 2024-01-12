package assessment.videos.cli.hashtags;

import assessment.videos.cli.videos.VideosClient;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="delete-video-hashtag", description="Deletes a hashtag from a video", mixinStandardHelpOptions = true)
public class DeleteVideoHashtagCommand implements Runnable {

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long hashtagId;

	@Inject
	private VideosClient client;

	@Override
	public void run() {
		HttpResponse<String> response = client.removeHashtag(videoId, hashtagId);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
