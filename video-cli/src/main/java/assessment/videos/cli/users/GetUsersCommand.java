package assessment.videos.cli.users;

import assessment.videos.cli.domain.User;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name="get-users", description="Gets all the users", mixinStandardHelpOptions = true)
public class GetUsersCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Override
	public void run() {
		for (User u : client.list()) {
			System.out.println(u);
		}
	}

}
