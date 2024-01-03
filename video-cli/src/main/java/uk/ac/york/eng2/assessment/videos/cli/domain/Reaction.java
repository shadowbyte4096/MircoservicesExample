package uk.ac.york.eng2.assessment.videos.cli.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Reaction {

	private Long id;
	private int reaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getReaction() {
		return reaction;
	}

	public void setReaction(int reaction) {
		this.reaction = reaction;
	}

	@Override
	public String toString() {
		return "Reaction [id=" + id + ", reaction=" + reaction + "]";
	}
}
