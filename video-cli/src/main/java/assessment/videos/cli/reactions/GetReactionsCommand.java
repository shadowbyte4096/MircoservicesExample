package assessment.videos.cli.reactions;

import assessment.videos.cli.domain.Reaction;
import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-reactions", description="Gets all the reactions", mixinStandardHelpOptions = true)
public class GetReactionsCommand implements Runnable {

	@Inject
	private ReactionsClient client;

	@Override
	public void run() {
		for (Reaction b : client.list()) {
			System.out.println(b);
		}
	}

}
