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
	/* protected region validate-body end */
	private Long id;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToMany(mappedBy="videos")
	private Set<Hashtag> hashtags;
	/* protected region validate-body end */
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToMany
	private Set<User> users;
	/* protected region validate-body end */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Set<User> getUsers() {
		return users;
	}

	public void setUsesr(Set<User> users) {
		this.users = users;
	}
	/* protected region validate-body end */

}
