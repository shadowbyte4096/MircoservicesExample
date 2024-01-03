package uk.ac.york.eng2.assessment.videos.cli.reactions;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import uk.ac.york.eng2.assessment.videos.cli.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.cli.domain.Video;

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
