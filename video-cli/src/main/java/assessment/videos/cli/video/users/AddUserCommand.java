package assessment.videos.cli.video.users;

import assessment.videos.cli.dto.UserDTO;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="add-user", description="Adds a user", mixinStandardHelpOptions = true)
public class AddUserCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Parameters(index="0")
	private String username;

	@Override
	public void run() {
		UserDTO dto = new UserDTO();
		dto.setUsername(username);

		HttpResponse<String> response = client.AddUser(dto);
		System.out.printf("Server responded with status %s: %s%n",
				response.getStatus(), response.getBody().orElse("(no text)"));
	}

}
