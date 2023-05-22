package com.siraj.players.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity(name="Sports")
@Table(name = "sports")
public class Sports {

    @Id

    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "sports_id"
    )

    private Long sportsId;

    @Column(name="sports_name", nullable = false, columnDefinition = "TEXT")
    private String sportsName;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "player_id")
    private Players player;

    public void setPlayer(Players player) {
        this.player = player;
    }

    public void setSportsId(Long sportsId) {
        this.sportsId = sportsId;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public Long getSportsId() {
        return sportsId;
    }

    public String getSportsName() {
        return sportsName;
    }

    public Players getPlayer() {
        return player;
    }

    public Sports(String sportsName) {
        this.sportsName = sportsName;
    }


    public Sports(String sportsName, Players player) {
        this.sportsName = sportsName;
        this.player = player;
    }

    public Sports(){

    }


}
