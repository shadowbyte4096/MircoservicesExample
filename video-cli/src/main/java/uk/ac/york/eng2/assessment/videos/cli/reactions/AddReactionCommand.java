package uk.ac.york.eng2.assessment.videos.cli.reactions;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.assessment.videos.cli.dto.ReactionDTO;
import uk.ac.york.eng2.assessment.videos.cli.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.cli.videos.VideosClient;

@Command(name="add-reaction", description="Adds a reaction to a video from a user", mixinStandardHelpOptions = true)
public class AddReactionCommand implements Runnable {

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
		HttpResponse<String> response = client.add(videoId, userId, dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
