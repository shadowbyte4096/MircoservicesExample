package uk.ac.york.eng2.assessment.videos.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class VideoDTO {

	private String title;
	private Integer year;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
