package assessment.videos.cli.subscription.users;

import assessment.videos.cli.dto.HashtagDTO;
import assessment.videos.cli.dto.UserDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="subscribe-to-video", description="Adds a subscription", mixinStandardHelpOptions = true)
public class AddSubscriptionsCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Parameters(index="0")
	private Long userId;
	
	@Parameters(index="1")
	private String hashtag;

	@Override
	public void run() {
		HashtagDTO dto = new HashtagDTO();
		dto.setName(hashtag);
		HttpResponse<String> response = client.AddSubscription(userId, dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
