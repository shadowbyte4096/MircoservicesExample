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
	@GeneratedValue
	private Long id;
	
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
	
	
	/* protected region validate-body on begin */
	public Hashtag getHashtag() {
		return hashtag;
	}

	public void setHashtag(Hashtag hashtag) {
		this.hashtag = hashtag;
	}
	/* protected region validate-body end */

}
