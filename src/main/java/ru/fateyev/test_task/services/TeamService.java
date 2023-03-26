package ru.fateyev.test_task.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fateyev.test_task.models.Player;
import ru.fateyev.test_task.models.Team;
import ru.fateyev.test_task.repositories.TeamRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public List<Team> findAllByKindsOfSport(String kindsOfSport) {
        return teamRepository.readAllByKindOfSport(kindsOfSport);
    }

    public List<Team> findAllByFoundingDateBetween(Date startDate, Date endDate) {
        return teamRepository.readAllByFoundingDateBetween(startDate, endDate);
    }

    @Transactional
    public void save(Team team){
        teamRepository.save(team);
    }

    @Transactional
    public void update(int id, Team updatedTeam) {
        updatedTeam.setId(id);
        teamRepository.save(updatedTeam);
    }

    @Transactional
    public void delete(int id){
        teamRepository.deleteById(id);
    }

    public List<Player> getPlayersByTeamId(int id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isPresent()) {
            Hibernate.initialize(team.get().getPlayers());
            return team.get().getPlayers();
        }
        return Collections.emptyList();
    }

    @Transactional
    public void addPlayer(int id, Player player){
        Optional<Team> team = teamRepository.findById(id);

        team.get().getPlayers().add(player);
        teamRepository.save(team.get());
    }
}
