package assessment.videos.cli.subscription.videos;

import assessment.videos.cli.dto.HashtagDTO;
import io.micronaut.http.HttpResponse;
import assessment.videos.cli.domain.Hashtag;
import assessment.videos.cli.domain.Video;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name="get-suggestions", description="Gets 10 video suggestions for a given user and hashtag", mixinStandardHelpOptions = true)
public class GetSuggestionsCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private Long userId;
	
	@Parameters(index="1")
	private String hashtag;
	
	@Override
	public void run() {		
		HashtagDTO dto = new HashtagDTO();
		dto.setName(hashtag);
		Iterable<Video> videos = client.GetSuggestions(userId, dto);
		for (Video video : videos) {
			System.out.println(video);
		}
	}

}
