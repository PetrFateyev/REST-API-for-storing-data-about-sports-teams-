package ru.fateyev.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fateyev.test_task.models.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
}
