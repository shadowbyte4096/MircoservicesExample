package assessment.videos.cli.users;

import assessment.videos.cli.domain.User;
import assessment.videos.cli.dto.UserDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${users.url:`http://localhost:8080/users`}")
public interface UsersClient {

	@Get("/")
	Iterable<User> list();

	@Post("/")
	HttpResponse<Void> add(@Body UserDTO userDetails);

	@Get("/{id}")
	UserDTO getUser(long id);

	@Put("/{id}")
	HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails);

	@Delete("/{id}")
	HttpResponse<Void> deleteUser(long id);
}
