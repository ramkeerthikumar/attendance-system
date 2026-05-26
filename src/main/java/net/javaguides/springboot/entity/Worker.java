package net.javaguides.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "workers",
        indexes = {
                @Index(name = "idx_worker_phone", columnList = "phone")
        }
)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Designation designation;

    @Column(nullable = false)
    private Double dailyWageRate;

    @Column(nullable = false)
    private boolean active = true;

    public Worker() {
    }

    public Worker(Long id, String name, String phone,
                  Designation designation,
                  Double dailyWageRate,
                  boolean active) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.designation = designation;
        this.dailyWageRate = dailyWageRate;
        this.active = active;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Double getDailyWageRate() {
        return dailyWageRate;
    }

    public void setDailyWageRate(Double dailyWageRate) {
        this.dailyWageRate = dailyWageRate;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}