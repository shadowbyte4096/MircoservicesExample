package assessment.videos.cli.reactions;

import assessment.videos.cli.dto.ReactionDTO;
import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name="update-reaction", description="Updates a reaction", mixinStandardHelpOptions = true)
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
		ReactionDTO dto = new ReactionDTO();
		dto.setReaction(reaction);
		HttpResponse<String> response = client.updateReaction(videoId, userId, dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}
}
