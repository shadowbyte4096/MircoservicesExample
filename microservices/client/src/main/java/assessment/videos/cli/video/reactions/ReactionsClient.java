package assessment.videos.cli.video.reactions;


import assessment.videos.cli.domain.Reaction;
import assessment.videos.cli.dto.ReactionDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${reactions.url:`http://localhost:8080/reaction`}")
public interface ReactionsClient {

	@Post("/{videoId}/{userId}")
	HttpResponse<String> AddReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);

	@Get("/{videoId}/{userId}")
	Reaction GetReaction(long videoId, long userId);

	@Put("/{videoId}/{userId}")
	HttpResponse<String> UpdateReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);
}
