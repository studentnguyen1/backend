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

import vn.khanguyen.backend.domain.Job;
import vn.khanguyen.backend.domain.dto.ResultPaginationDTO;
import vn.khanguyen.backend.domain.res.job.ResJobDTO;
import vn.khanguyen.backend.service.JobService;
import vn.khanguyen.backend.util.annotation.ApiMessage;
import vn.khanguyen.backend.util.error.ResourceNotFoundException;

@RestController
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    @ApiMessage("Create a job")
    public ResponseEntity<ResJobDTO> createJob(@RequestBody Job job) {
        Job createdJob = this.jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.convertToJobDTO(createdJob));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update a job")
    public ResponseEntity<ResJobDTO> updateJob(@RequestBody Job job) throws ResourceNotFoundException {
        Job updatedJob = this.jobService.updateJob(job);
        if (updatedJob == null) {
            throw new ResourceNotFoundException("Job with id " + job.getId() + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.convertToJobDTO(updatedJob));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete a job")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) throws ResourceNotFoundException {
        if (this.jobService.deleteJob(id) == null) {
            throw new ResourceNotFoundException("Job with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("Fetch job by id")
    public ResponseEntity<ResJobDTO> getJobById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Job job = this.jobService.getJobById(id);
        if (job == null) {
            throw new ResourceNotFoundException("Job with id " + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.convertToJobDTO(job));
    }

    @GetMapping("/jobs")
    @ApiMessage("Fetch all jobs")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(@Filter Specification<Job> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.getAllJobs(spec, pageable));
    }
}
