package assessment.base;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
import assessment.dto.VideoDTO;

@Client("/")
public interface BaseClient {
	
	@Get("/VideoMicroservice/")
	HttpResponse<Void> GetVideoMicroserviceHealth();

}
