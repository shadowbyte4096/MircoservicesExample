package uk.ac.york.eng2.assessment.videos.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Reaction {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Integer reaction;

	@JsonIgnore
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@ManyToOne
	private Video video;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getReaction() {
		return reaction;
	}

	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
