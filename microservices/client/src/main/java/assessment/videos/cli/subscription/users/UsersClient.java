package assessment.videos.cli.subscription.users;

import assessment.videos.cli.domain.Hashtag;
import assessment.videos.cli.dto.HashtagDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("${users.url:`http://localhost:8082/user`}")
public interface UsersClient {
	
	@Get("/{id}")
	Iterable<Hashtag> ListSubscriptions(long id);
	
	@Post("/{userId}")
	HttpResponse<String> AddSubscription(long userId, @Body HashtagDTO details);
	
	@Delete("/{userId}")
	HttpResponse<String> DeleteSubscription(long userId, @Body HashtagDTO details);
}
