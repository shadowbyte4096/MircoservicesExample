package assessment.controllers;

import java.net.URI;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import assessment.domain.User;
import assessment.dto.UserDTO;

@Controller("/user")
public class UsersControllerExt extends UsersController{
	
	@Override
	@Get("/")
	public Iterable<User> ListUsers() {
		return userRepo.findAll();
	}
	
	@Override
	@Post("/")
	public HttpResponse<String> AddUser(@Body UserDTO details) {
		User user = new User();
		user.setUsername(details.getUsername());

		userRepo.save(user);
		
		producer.UserAdded(user.getId(), user);

		return HttpResponse.created(URI.create("/users/" + user.getId()));
	}

	@Override
	@Get("/{id}")
	public UserDTO GetUser(long id) {
		return userRepo.findOne(id).orElse(null);
	}
}