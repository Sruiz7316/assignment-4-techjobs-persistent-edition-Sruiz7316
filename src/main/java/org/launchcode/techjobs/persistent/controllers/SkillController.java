package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {
	@Autowired
	private SkillRepository skillRepository;

	@GetMapping("")
	public String index(Model model) {
		List<Skill> skills = new ArrayList<>();
		final Iterable<Skill> all = skillRepository.findAll();
		for (Skill skill : all) {
			skills.add(skill);
		}
		model.addAttribute("skills", skills);
		return "skills/index";
	}

	@GetMapping("add")
	public String displayAddSkillForm(Model model) {
		model.addAttribute(new Skill());
		return "skills/add";
	}

	@PostMapping("add")
	public String processAddSkillForm(
		@ModelAttribute @Valid Skill skill,
		Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "skills/add";
		} else {
			skillRepository.save(skill);
		}

		return "redirect:";
	}

	@GetMapping("view/{skillId}")
	public String displayViewSkill(Model model, @PathVariable int skillId) {

		Optional<Skill> optSkill = skillRepository.findById(skillId);
		if (optSkill.isPresent()) {
			Skill skill = optSkill.get();
			model.addAttribute("skill", skill);
			return "skills/view";
		} else {
			return "redirect:../";
		}
	}
}
