package uk.ac.york.eng2.assessment.videos.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.UserDTO;
import uk.ac.york.eng2.assessment.videos.repositories.ReactionRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

@Controller("/users")
public class UsersController {

	@Inject
	UsersRepository repo;
	
	@Inject
	ReactionRepository reactionRepo;

	@Get("/")
	public Iterable<User> list() {
		return repo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body UserDTO userDetails) {
		User user = new User();
		user.setUsername(userDetails.getUsername());
		repo.save(user);
		return HttpResponse.created(URI.create("/users/" + user.getId()));
	}

	@Get("/{id}")
	public UserDTO getUser(long id) {
		return repo.findOne(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound();
		}

		User user = oUser.get();
		if (userDetails.getUsername() != null) {
			user.setUsername(userDetails.getUsername());
		}
		repo.save(user);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteUser(long id) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound();
		}
		User user = oUser.get();
		
		//delete leftover reactions
		for (Reaction reaction: reactionRepo.findAll()) {
			if (reaction.getUser().getId() != user.getId()) {
				continue;
			}
			else {
				reactionRepo.delete(reaction);
			}
		}
		
		repo.delete(user);
		return HttpResponse.ok();
	}

}
