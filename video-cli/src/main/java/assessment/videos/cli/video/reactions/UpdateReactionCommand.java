package assessment.videos.cli.video.reactions;

import assessment.videos.cli.dto.ReactionDTO;
import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name="react-to-video", description="Adds or updates a reaction to a video", mixinStandardHelpOptions = true)
public class UpdateReactionCommand implements Runnable {

	@Inject
	private ReactionsClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long userId;
	
	@Parameters(index="2")
	private int reaction;

	@Override
	public void run() {
		if (reaction == -1 || reaction == 1) {
			ReactionDTO dto = new ReactionDTO();
			dto.setReaction(reaction);
			HttpResponse<String> response = client.UpdateReaction(videoId, userId, dto);
			System.out.printf("Server responded with status %s: %s%n",
					response.getStatus(), response.getBody().orElse("(no text)"));
		}
		else {
			System.out.printf("reaction must be 1 or -1 for like or dislike");
		}
	}
}
