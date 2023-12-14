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
public class Video {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String title;

	@JsonIgnore
	@OneToMany(mappedBy="video")
	private Set<Hashtag> hashtags;
	
	@JsonIgnore
	@OneToMany(mappedBy="video")
	private Set<Reaction> reactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<Reaction> reactions) {
		this.reactions = reactions;
	}

	public Set<Hashtag> getHashtags() {
		return hashtags;
	}
	
	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

}
