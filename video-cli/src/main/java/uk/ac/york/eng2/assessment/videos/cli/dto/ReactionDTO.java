package uk.ac.york.eng2.assessment.videos.cli.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ReactionDTO {

	private int reaction;

	public int getReaction() {
		return reaction;
	}

	public void setReaction(int reaction) {
		this.reaction = reaction;
	}
	
	@Override
	public String toString() {
		return "ReactionDTO [reaction=" + reaction + "]";
	}
}
