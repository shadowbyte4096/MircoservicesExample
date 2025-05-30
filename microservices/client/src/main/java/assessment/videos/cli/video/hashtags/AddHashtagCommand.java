package assessment.videos.cli.video.hashtags;

import assessment.videos.cli.dto.HashtagDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-hashtag", description="Adds a hashtag to a video", mixinStandardHelpOptions = true)
public class AddHashtagCommand implements Runnable {

	@Inject
	private HashtagsClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private String hashtagName;

	@Override
	public void run() {
		HashtagDTO dto = new HashtagDTO();
		dto.setName(hashtagName);
		HttpResponse<String> response = client.AddHashtag(videoId, dto);
		System.out.printf("Server responded with status %s: %s%n",
			response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
