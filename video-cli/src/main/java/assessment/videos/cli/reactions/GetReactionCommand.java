package assessment.videos.cli.reactions;

import assessment.videos.cli.domain.User;
import assessment.videos.cli.dto.ReactionDTO;
import assessment.videos.cli.dto.VideoDTO;
import assessment.videos.cli.videos.VideosClient;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-reaction", description="Gets a specific reaction", mixinStandardHelpOptions = true)
public class GetReactionCommand implements Runnable {

	@Inject
	private ReactionsClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		ReactionDTO reaction = client.getReaction(id);
		if (reaction == null) {
			System.err.println("Reaction not found!");
		} else {
			System.out.println(reaction);
		}
	}
	
}
