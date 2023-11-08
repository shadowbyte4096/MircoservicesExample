package uk.ac.york.eng2.assessment.videos.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserDTO {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
