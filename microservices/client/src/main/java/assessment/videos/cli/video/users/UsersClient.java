package assessment.videos.cli.video.users;

import assessment.videos.cli.domain.User;
import assessment.videos.cli.dto.UserDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("${users.url:`http://localhost:8080/user`}")
public interface UsersClient {

	@Get("/")
	Iterable<User> ListUsers();

	@Post("/")
	HttpResponse<String> AddUser(@Body UserDTO userDetails);

	@Get("/{id}")
	UserDTO GetUser(long id);
}
