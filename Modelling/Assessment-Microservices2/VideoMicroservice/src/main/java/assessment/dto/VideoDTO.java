package assessment.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class VideoDTO {
	
	private String title;

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
