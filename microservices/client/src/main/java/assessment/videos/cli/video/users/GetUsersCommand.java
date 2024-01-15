package assessment.videos.cli.video.users;

import assessment.videos.cli.domain.User;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-users", description="Gets all the users", mixinStandardHelpOptions = true)
public class GetUsersCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Override
	public void run() {
		for (User u : client.ListUsers()) {
			System.out.println(u);
		}
	}

}
