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
	/* protected region validate-body on begin */
	/* protected region validate-body end */
	private Long id;
	
	@Column(nullable = false)
	private int reaction;
	
	@Column(nullable = false)
	private String timecreated;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToOne
	private Hashtag hashtag;
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
	
	public String getTimeCreated() {
		return timecreated;
	}

	public void setTimeCreated(String timecreated) {
		this.timecreated = timecreated;
	}
	
	
	/* protected region validate-body on begin */
	public Hashtag getHashtag() {
		return hashtag;
	}

	public void setHashtag(Hashtag hashtag) {
		this.hashtag = hashtag;
	}
	/* protected region validate-body end */

}
