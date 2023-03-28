package ru.fateyev.test_task.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "team_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String teamName;

    @Column(name = "kind_of_sport")
    @NotEmpty(message = "Kind of sport should not be empty")
    @Size(min = 2, max = 100, message = "Kind of sport should be between 2 and 100 characters")
    private String kindOfSport;

    @Column(name = "founding_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date foundingDate;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    public Team(){}

    public Team(String teamName, String kindOfSport, Date foundingDate) {
        this.teamName = teamName;
        this.kindOfSport = kindOfSport;
        this.foundingDate = foundingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public Date getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(Date foundingDate) {
        this.foundingDate = foundingDate;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
