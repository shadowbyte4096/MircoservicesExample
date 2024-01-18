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
public class User {

	@Id
	/* protected region validate-body on begin */
	@GeneratedValue
	/* protected region validate-body end */
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@OneToMany(mappedBy="user")
	private Set<Reaction> reactions;
	/* protected region validate-body end */
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@OneToMany
	private Set<Video> videos;
	/* protected region validate-body end */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/* protected region validate-body on begin */
	public Set<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<Reaction> reactions) {
		this.reactions = reactions;
	}
	/* protected region validate-body end */
	
	
	/* protected region validate-body on begin */
	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}
	/* protected region validate-body end */

}
