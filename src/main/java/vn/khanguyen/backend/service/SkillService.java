package vn.khanguyen.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.khanguyen.backend.domain.Skill;
import vn.khanguyen.backend.domain.dto.Meta;
import vn.khanguyen.backend.domain.dto.ResultPaginationDTO;
import vn.khanguyen.backend.repository.SkillRepository;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill updateSkill(Skill skillCur) {
        Skill skill = this.skillRepository.findById(skillCur.getId()).orElse(null);
        if (skill == null) {
            return null;
        }
        skill.setName(skillCur.getName());
        return this.skillRepository.save(skill);
    }

    public Skill deleteSkill(Long id) {
        Skill skill = this.skillRepository.findById(id).orElse(null);
        if (skill == null) {
            return null;
        }
        skill.getJobs().forEach(job -> job.getSkills().remove(skill));

        this.skillRepository.delete(skill);
        return skill;
    }

    public Skill getSkillById(long id) {
        return this.skillRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO getAllSkills(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageSkill.getNumber() + 1);
        mt.setPageSize(pageSkill.getSize());
        mt.setPages(pageSkill.getTotalPages());
        mt.setTotal(pageSkill.getTotalElements());

        rs.setMeta(mt);
        List<Skill> listSkills = pageSkill.getContent();
        rs.setResult(listSkills);

        return rs;
    }
}
