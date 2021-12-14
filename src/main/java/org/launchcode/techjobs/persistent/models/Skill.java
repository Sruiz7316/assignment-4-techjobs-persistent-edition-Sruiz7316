package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

	@Size(max = 200)
	private String description;

	@ManyToMany(mappedBy = "skills")
	private List<Job> jobs;

	public Skill() {
		this.jobs = new ArrayList<>();
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> job) {
		this.jobs = job;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}