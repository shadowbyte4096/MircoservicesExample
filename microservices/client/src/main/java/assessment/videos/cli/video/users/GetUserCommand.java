package assessment.videos.cli.video.users;

import assessment.videos.cli.dto.UserDTO;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-user", description="Gets a specific user", mixinStandardHelpOptions = true)
public class GetUserCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		UserDTO user = client.GetUser(id);
		if (user == null) {
			System.err.println("User not found!");
			System.exit(1);
		} else {
			System.out.println(user);
		}
	}

	
}
