package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private JobRepository jobRepository;

	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("title", "My Jobs");
		List<Job> jobs = (List<Job>) jobRepository.findAll();
		model.addAttribute("jobs", jobs);
		return "index";
	}

	@GetMapping("add")
	public String displayAddJobForm(Model model) {
		model.addAttribute("title", "Add Job");
		final List<Skill> skills = (List<Skill>) skillRepository.findAll();
		final List<Employer> employers = (List<Employer>) employerRepository.findAll();
		model.addAttribute("skills", skills);
		model.addAttribute("employers", employers);
		model.addAttribute(new Job());
		return "add";
	}

	@PostMapping("add")
	public String processAddJobForm(
		@ModelAttribute @Valid Job newJob,
		Errors errors, Model model,
		@RequestParam int employerId, @RequestParam List<Integer> skills) {
		if (!errors.hasErrors()) {
			final List<Skill> skillRepositoryAllById = (List<Skill>) skillRepository.findAllById(skills);
			newJob.setSkills(skillRepositoryAllById);
			final Optional<Employer> employer = employerRepository.findById(employerId);
			employer.ifPresent(newJob::setEmployer);
			jobRepository.save(newJob);
			return "add";
		}

		return "redirect:";
	}

	@GetMapping("view/{jobId}")
	public String displayViewJob(Model model, @PathVariable int jobId) {
		final Optional<Job> byId = jobRepository.findById(jobId);
		byId.ifPresent(value -> model.addAttribute("job", value));
		return "view";
	}


}
