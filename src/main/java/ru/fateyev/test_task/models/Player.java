package ru.fateyev.test_task.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "team_number", unique = true)
    @Min(value = 1, message = "Number should be greater than 1")
    @Max(value = 1000, message = "Number should be less than 1000")
    private int teamNumber;

    @Column(name = "surname")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 50, message = "Surname should be between 2 and 50 characters")
    private String surname;

    @Column(name = "firstname")
    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 2, max = 50, message = "Firstname should be between 2 and 50 characters")
    private String firstname;

    @Column(name = "secondname")
    @NotEmpty(message = "Secondname should not be empty")
    @Size(min = 2, max = 50, message = "Secondname should be between 2 and 50 characters")
    private String secondname;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @Column(name = "position")
    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2, max = 100, message = "Position should be between 2 and 100 characters")
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PLAYERS_TEAM_ID"))
    @JsonBackReference
    private Team team;

    public Player() {
    }

    public Player(int teamNumber, String surName, String firstName, String secondName, Date birthDay, String position) {
        this.teamNumber = teamNumber;
        this.surname = surName;
        this.firstname = firstName;
        this.secondname = secondName;
        this.birthday = birthDay;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getSurName() {
        return surname;
    }

    public void setSurName(String surName) {
        this.surname = surName;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getSecondName() {
        return secondname;
    }

    public void setSecondName(String secondName) {
        this.secondname = secondName;
    }

    public Date getBirthDay() {
        return birthday;
    }

    public void setBirthDay(Date birthDay) {
        this.birthday = birthDay;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
