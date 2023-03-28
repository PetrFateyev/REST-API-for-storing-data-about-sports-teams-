package ru.fateyev.test_task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fateyev.test_task.models.Player;
import ru.fateyev.test_task.models.Team;
import ru.fateyev.test_task.repositories.PlayerRepository;
import ru.fateyev.test_task.repositories.TeamRepository;
import ru.fateyev.test_task.util.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void save(int teamId, Player player){
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Team team = teamOptional.get();
        player.setTeam(team);
        playerRepository.save(player);

        if (team.getPlayers() == null) {
            List<Player> playerList = new ArrayList<>();
            playerList.add(player);
            team.setPlayers(playerList);
        } else {
            team.getPlayers().add(player);
        }
    }

    @Transactional
    public void update(int id, Player updatedPlayer) {
        int teamId = playerRepository.findById(id).get().getTeam().getId();
        updatedPlayer.setTeam(teamRepository.findById(teamId).get());
        updatedPlayer.setId(id);
        playerRepository.save(updatedPlayer);
    }

    @Transactional
    public void delete(int id){
        Optional<Player> player = playerRepository.findById(id);
        if (!player.isPresent()) {
            throw new ResourceNotFoundException();
        }
        playerRepository.deleteById(id);
    }

    @Transactional
    public void changeTeam(int playerId, int teamId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!playerOptional.isPresent() || !teamOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }
        playerRepository.deleteById(playerId);
        Player player = playerOptional.get();
        player.setTeam(teamOptional.get());
        player.setId(playerId);
        playerRepository.save(player);
    }
}
