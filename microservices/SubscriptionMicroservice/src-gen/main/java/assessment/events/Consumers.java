package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import javax.transaction.Transactional;
import jakarta.inject.Inject;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.repositories.UserRepository;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;

@KafkaListener(groupId = "SubscriptionMicroserviceConsumers")
public class Consumers {
	
	@Inject
	UserRepository userRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	//@Transactional
	//@Topic("VideoAdded") // uncomment on src 
	public void VideoAdded(@KafkaKey Long id, Video video) {
	}
	
	//@Transactional
	//@Topic("HashtagAdded") // uncomment on src 
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
	}
	
	//@Transactional
	//@Topic("UserAdded") // uncomment on src 
	public void UserAdded(@KafkaKey Long id, User user) {
	}
	
	//@Transactional
	//@Topic("VideoWatched") // uncomment on src 
	public void VideoWatched(@KafkaKey Long id, User user) {
	}
}