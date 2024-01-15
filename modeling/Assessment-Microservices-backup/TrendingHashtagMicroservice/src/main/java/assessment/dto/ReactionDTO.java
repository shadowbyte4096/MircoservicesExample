package assessment.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ReactionDTO {
	
	private int reaction;
	
	private String timecreated;

	
	public int getReaction() {
		return reaction;
	}

	public void setReaction(int reaction) {
		this.reaction = reaction;
	}
	
	public String getTimeCreated() {
		return timecreated;
	}

	public void setTimeCreated(String timecreated) {
		this.timecreated = timecreated;
	}
}
