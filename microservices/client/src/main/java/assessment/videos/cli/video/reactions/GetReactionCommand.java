package assessment.videos.cli.video.reactions;

import assessment.videos.cli.domain.Reaction;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-reaction", description="Gets a specific reaction", mixinStandardHelpOptions = true)
public class GetReactionCommand implements Runnable {

	@Inject
	private ReactionsClient client;

	@Parameters(index="0")
	private Long videoId;

	@Parameters(index="1")
	private Long userId;

	@Override
	public void run() {
		Reaction reaction = client.GetReaction(videoId, userId);
		if (reaction == null) {
			System.err.println("Reaction not found!");
		} else {
			System.out.println(reaction);
		}
	}
	
}
