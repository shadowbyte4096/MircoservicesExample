package assessment.users;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.User;
import assessment.dto.UserDTO;

@Client("/user")
public interface UsersClient {
	
	@Get("/")
	Iterable<User> ListUsers();

	@Post("/")
	HttpResponse<String> AddUser(@Body UserDTO userDetails);

	@Get("/{id}")
	UserDTO GetUser(long id);

}
