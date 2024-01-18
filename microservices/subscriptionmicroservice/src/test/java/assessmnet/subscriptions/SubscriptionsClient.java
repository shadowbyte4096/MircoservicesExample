package assessmnet.subscriptions;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import assessment.dto.HashtagDTO;
import assessment.domain.Hashtag;

@Client("/user")
public interface SubscriptionsClient {
	
	@Get("/{id}")
	Iterable<Hashtag> ListSubscriptions(long id);
	
	@Post("/{userId}")
	HttpResponse<String> AddSubscription(long userId, @Body HashtagDTO details);
	
	@Delete("/{userId}")
	HttpResponse<String> DeleteSubscription(long userId, @Body HashtagDTO details);

}
