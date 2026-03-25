package vn.khanguyen.backend.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.khanguyen.backend.domain.Skill;
import vn.khanguyen.backend.domain.dto.ResultPaginationDTO;
import vn.khanguyen.backend.service.SkillService;
import vn.khanguyen.backend.util.annotation.ApiMessage;
import vn.khanguyen.backend.util.error.ResourceNotFoundException;

@RestController
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @ApiMessage("Create a skill")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.createSkill(skill));
    }

    @PutMapping("/skills")
    @ApiMessage("Update a skill")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill) throws ResourceNotFoundException {
        Skill updatedSkill = this.skillService.updateSkill(skill);
        if (updatedSkill == null) {
            throw new ResourceNotFoundException("Skill with id " + skill.getId() + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedSkill);
    }

    @DeleteMapping("/skills/{id}")
    @ApiMessage("Delete a skill")
    public ResponseEntity<Void> deleteSkill(@PathVariable("id") long id) throws ResourceNotFoundException {
        if (this.skillService.deleteSkill(id) == null) {
            throw new ResourceNotFoundException("Skill with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/skills/{id}")
    @ApiMessage("Fetch skill by id")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Skill skill = this.skillService.getSkillById(id);
        if (skill == null) {
            throw new ResourceNotFoundException("Skill with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(skill);
    }

    @GetMapping("/skills")
    @ApiMessage("Fetch all skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(@Filter Specification<Skill> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.getAllSkills(spec, pageable));
    }
}
