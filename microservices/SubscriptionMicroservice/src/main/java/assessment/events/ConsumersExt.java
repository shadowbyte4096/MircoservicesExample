package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import assessment.domain.User;
import assessment.domain.Video;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import assessment.domain.Hashtag;
import assessment.repositories.UserRepository;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;

@KafkaListener(groupId = "SubscriptionMicroserviceConsumers")
public class ConsumersExt extends Consumers{
	
	@Inject
	UserRepository userRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Override
	@Topic("VideoAdded")
	public void VideoAdded(@KafkaKey Long id, Video video) {
		video.setId(id);
		videoRepo.save(video);
	}
	
	@Override
	@Topic("HashtagAdded")
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtagDTO) {
		Hashtag hashtag = new Hashtag();
		hashtag.setName(hashtagDTO.getName());
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			//oh No
			return;
		}
		Video video = oVideo.get();
		
		Iterable<Hashtag> hashtags = hashtagRepo.findAll();
		for (Hashtag repoHashtag : hashtags) {
			if (repoHashtag.getName().equals(hashtag.getName())) {
				Set<Video> vids = hashtag.getVideos();
				if (vids == null) {
					vids = new HashSet<Video>();
				}
				vids.add(video);
				hashtag.setVideos(vids);
				hashtagRepo.update(hashtag);
				return;
			}
		}
		hashtagRepo.save(hashtag);
		Set<Video> vids = new HashSet<Video>();
		vids.add(video);
		hashtag.setVideos(vids);
		hashtagRepo.update(hashtag);
	}
	
	@Override
	@Topic("UserAdded")
	public void UserAdded(@KafkaKey Long id, User user) {
		user.setId(id);
		userRepo.save(user);
	}
	
	@Override
	@Transactional
	@Topic("VideoWatched")
	public void VideoWatched(@KafkaKey Long id, User user) {
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			//Oh no
		}
		Video video = oVideo.get();
		
		Optional<User> oUser = userRepo.findById(user.getId());
		if (oUser.isEmpty()) {
			//Oh no
		}
		User repoUser = oUser.get();
		repoUser.getVideos().add(video);
		userRepo.update(repoUser);
		
	}
}