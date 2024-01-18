package assessment.videos.cli.video.reactions;

import assessment.videos.cli.dto.ReactionDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="watch-video", description="watchs a video", mixinStandardHelpOptions = true)
public class AddReactionCommand implements Runnable {

	@Inject
	private ReactionsClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long userId;

	@Override
	public void run() {
		ReactionDTO dto = new ReactionDTO();
		dto.setReaction(0);
		HttpResponse<String> response = client.AddReaction(videoId, userId, dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
