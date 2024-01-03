package uk.ac.york.eng2.assessment.videos.cli.reactions;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.assessment.videos.cli.domain.Video;
import uk.ac.york.eng2.assessment.videos.cli.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.cli.domain.User;
import uk.ac.york.eng2.assessment.videos.cli.dto.ReactionDTO;
import uk.ac.york.eng2.assessment.videos.cli.dto.VideoDTO;

@Client("${reactions.url:`http://localhost:8080/reactions`}")
public interface ReactionsClient {

	@Get("/")
	Iterable<Reaction> list();

	@Post("/{videoId}/{userId}")
	HttpResponse<String> add(long videoId, long userId, @Body ReactionDTO reactionDetails);

	@Get("/{id}")
	ReactionDTO getReaction(long id);

	@Put("/{videoId}/{userId}")
	HttpResponse<String> updateReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);
}
