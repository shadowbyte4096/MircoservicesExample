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
public class Hashtag {

	@Id
	/* protected region validate-body on begin */
	/* protected region validate-body end */
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@JsonIgnore
	/* protected region validate-body on begin */
	@ManyToMany
	private Set<Video> videos;
	/* protected region validate-body end */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	/* protected region validate-body on begin */
	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}
	/* protected region validate-body end */

}
