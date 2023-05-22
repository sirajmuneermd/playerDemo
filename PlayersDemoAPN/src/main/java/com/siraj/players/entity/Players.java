package com.siraj.players.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Players")
@Table(
        name = "players",
        uniqueConstraints = {@UniqueConstraint(name = "player_email_unique", columnNames = "email")}
)
public class Players {

    @Id
    @SequenceGenerator(
            name = "player_id",
            sequenceName = "player_id",
            allocationSize = 5,
            initialValue = 100001
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_id"
    )
    @Column(name = "player_id", updatable = false)
    private Long playerId;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String emailId;

    @Column(name = "player_level", nullable = false)
    private Integer playerLevel;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false, columnDefinition = "TEXT")
    private String gender;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @OneToMany(
            mappedBy = "player",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Sports> sports;

    public Long getPlayerId() {
        return playerId;
    }

    public String getEmailId() {
        return emailId;
    }

    public Integer getPlayerLevel() {
        return playerLevel;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Sports> getSports() {
        return sports;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPlayerLevel(Integer playerLevel) {
        this.playerLevel = playerLevel;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSports(List<Sports> sports) {
        this.sports = sports;
    }

    public Players() {

    }

    public Players(String emailId, Integer playerLevel, Integer age, String gender, String firstName, String lastName, List<Sports> sports) {
        this.emailId = emailId;
        this.playerLevel = playerLevel;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sports = sports;
    }

    public Players(String emailId, Integer playerLevel, Integer age, String gender, String firstName, String lastName) {
        this.emailId = emailId;
        this.playerLevel = playerLevel;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Players{" +
                "playerId=" + playerId +
                ", emailId='" + emailId + '\'' +
                ", playerLevel=" + playerLevel +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
