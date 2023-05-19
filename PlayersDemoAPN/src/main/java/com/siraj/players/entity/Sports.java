package com.siraj.players.entity;

import jakarta.persistence.*;


@Entity(name="Sports")
@Table(name = "sports")
public class Sports {

    @Id
    @SequenceGenerator(
            name = "sports_id",
            sequenceName="sports_id",
            allocationSize = 5,
            initialValue =  100001

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_id"
    )

    private Long sportsId;

    @Column(name="sports_name", nullable = false,columnDefinition = "TEXT")
    private String sportsName;


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


    public Sports(String sportsName) {
        this.sportsName = sportsName;
    }


    public Sports(Long sportsId, String sportsName) {
        this.sportsId = sportsId;
        this.sportsName = sportsName;
    }

    public Sports(){

    }
}
