package ru.fateyev.test_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.fateyev.test_task.models.Player;
import ru.fateyev.test_task.models.Team;
import ru.fateyev.test_task.services.TeamService;
import ru.fateyev.test_task.util.ResourceNotCreatedException;
import ru.fateyev.test_task.util.ResourceErrorResponse;
import ru.fateyev.test_task.util.ResourceNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //Метод для получения всех команд
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getTeams() {
        return teamService.findAll();
    }

    //Метод для получения всех команд, с фильтрацией по виду спорта
    @GetMapping( value = "/{kindsOfSport}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getTeamsByKindsOfSport(@PathVariable("kindsOfSport") String kindsOfSport) {
        return teamService.findAllByKindsOfSport(kindsOfSport);
    }

    //Метод для получения всех команд, с возможностью получить команды за период, по дате основания
    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getTeamsByFoundingDate(@RequestParam Date startDate, @RequestParam Date endDate) {
        return teamService.findAllByFoundingDateBetween(startDate, endDate);
    }

    //Метод для получения всех участников конкретной команды
    @GetMapping("/{id}/players")
    public List<Player> getPlayersByTeam(@PathVariable("id") int id) {
        return teamService.getPlayersByTeamId(id);
    }

    //Метод для получения всех участников конкретной команды по конкретной позиции
    @GetMapping("/{id}/players/{position}")
    public List<Player> getPlayersByTeam(@PathVariable("id") int id, @PathVariable("position") String position) {
        return teamService.getPlayersByTeamId(id, position);
    }

    //Метод для добавления команды
    @PostMapping
    public ResponseEntity<HttpStatus> createTeam(@RequestBody @Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMessage = new StringBuilder();
            List<FieldError> errorList = new ArrayList<>();

            for (FieldError fieldError: errorList) {
                errorsMessage.append(fieldError.getField())
                             .append(" - ").append(fieldError.getDefaultMessage())
                             .append(";");
            }

            throw new ResourceNotCreatedException(errorsMessage.toString());
        }
        teamService.save(team);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Метод для изменения данных команды
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTeam(@PathVariable("id") int id ,@RequestBody @Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMessage = new StringBuilder();
            List<FieldError> errorList = new ArrayList<>();

            for (FieldError fieldError: errorList) {
                errorsMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new ResourceNotCreatedException(errorsMessage.toString());
        }
        teamService.update(id,team);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Метод для удаления команды
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") int id){
        teamService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResourceErrorResponse> handleException(ResourceNotFoundException e) {
        ResourceErrorResponse response = new ResourceErrorResponse(
                "Team with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ResourceErrorResponse> handleException(ResourceNotCreatedException e) {
        ResourceErrorResponse response = new ResourceErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
