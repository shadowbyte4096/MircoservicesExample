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
public class Reaction {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private int reaction;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToOne
	private User user;
	/* protected region validate-body end */
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToOne
	private Video video;
	/* protected region validate-body end */
	
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
	
	
	/* protected region validate-body on begin */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	/* protected region validate-body end */
	
	
	/* protected region validate-body on begin */
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	/* protected region validate-body end */

}
