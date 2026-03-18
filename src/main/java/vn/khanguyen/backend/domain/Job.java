
package vn.khanguyen.backend.domain;

import java.sql.Date;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.khanguyen.backend.util.SecurityUtil;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "name",
        "location",
        "salary",
        "quantity",
        "level",
        "description",
        "startDate",
        "endDate",
        "isActive",
        "createdAt",
        "updatedAt",
        "createdBy",
        "updatedBy"
})
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String location;

    private double salary;

    private int quantity;

    private String level;

    private String description;

    private Date startDate;

    private Date endDate;

    private boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleAfterUpdate() {
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        this.updatedAt = Instant.now();
    }
}
