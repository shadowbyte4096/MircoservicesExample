package assessment.videos.cli.subscription.users;

import assessment.videos.cli.domain.Hashtag;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-user-subscriptions", description="Gets all the subcriptions for a user", mixinStandardHelpOptions = true)
public class GetSubscriptionsCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Parameters(index="0")
	private Long userId;
	
	@Override
	public void run() {
		Iterable<Hashtag> hashtags = client.ListSubscriptions(userId);
		if (hashtags == null) {
			System.out.println("[]");
			return;
		}
		for (Hashtag hashtag : hashtags) {
			System.out.println(hashtag);
		}
	}

}
