package assessment.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/* protected region validate-body on begin */
/* protected region validate-body end */

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Video {

	@Id
	/* protected region validate-body on begin */
	@GeneratedValue
	/* protected region validate-body end */
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToMany(mappedBy="videos")
	private Set<Hashtag> hashtags;
	/* protected region validate-body end */
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@OneToMany
	private Set<Reaction> reactions;
	/* protected region validate-body end */
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToOne
	private User user;
	/* protected region validate-body end */
	
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
	
	
	/* protected region validate-body on begin */
	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	/* protected region validate-body end */
	
	
	/* protected region validate-body on begin */
	public Set<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<Reaction> reactions) {
		this.reactions = reactions;
	}
	/* protected region validate-body end */
	
	
	/* protected region validate-body on begin */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	/* protected region validate-body end */

}
