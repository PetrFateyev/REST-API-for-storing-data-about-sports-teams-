package ru.fateyev.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fateyev.test_task.models.Player;
import ru.fateyev.test_task.models.Team;
import java.util.Date;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {

    List<Team> readAllByKindOfSport(String kindOfSport);

    List<Team> readAllByFoundingDateBetween(Date startDate, Date endDate);

}
