package de.tim.ipwa0201.ghostnet.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private String sizeEstimate;

    @Enumerated(EnumType.STRING)
    private GhostNetStatus status;

    @ManyToOne
    private Person reporter; // optional (kann null sein)

    @ManyToOne
    private Person salvager; // optional

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GhostNet() {
    }

    public GhostNet(double latitude, double longitude, String sizeEstimate, GhostNetStatus status, Person reporter) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sizeEstimate = sizeEstimate;
        this.status = status;
        this.reporter = reporter;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getter/Setter ab hier

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSizeEstimate() {
        return sizeEstimate;
    }

    public void setSizeEstimate(String sizeEstimate) {
        this.sizeEstimate = sizeEstimate;
    }

    public GhostNetStatus getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus status) {
        this.status = status;
    }

    public Person getReporter() {
        return reporter;
    }

    public void setReporter(Person reporter) {
        this.reporter = reporter;
    }

    public Person getSalvager() {
        return salvager;
    }

    public void setSalvager(Person salvager) {
        this.salvager = salvager;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
