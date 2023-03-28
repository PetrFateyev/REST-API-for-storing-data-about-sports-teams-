package ru.fateyev.test_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.fateyev.test_task.models.Player;
import ru.fateyev.test_task.services.PlayerService;
import ru.fateyev.test_task.util.ResourceNotCreatedException;
import ru.fateyev.test_task.util.ResourceNotFoundException;
import ru.fateyev.test_task.util.ResourceErrorResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/team")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //Метод для добавления игрока
    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> createPlayer(@PathVariable(value = "id") int teamId,@RequestBody @Valid Player player, BindingResult bindingResult) {
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
        playerService.save(teamId, player);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Метод для изменения данных о игроке
    @PutMapping("/{id}/players/{playerId}")
    public ResponseEntity<HttpStatus> updatePlayer(@PathVariable("playerId") int playerId ,
                                                   @RequestBody @Valid Player player, BindingResult bindingResult) {
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
        playerService.update(playerId,player);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Метод для смены команды
    @PutMapping("/{id}/players/{playerId}/change/{newId}")
    public ResponseEntity<HttpStatus> updatePlayer(@PathVariable("playerId") int playerId ,
                                                   @PathVariable(value = "newId") int teamId) {
        playerService.changeTeam(playerId, teamId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Метод для удаления игрока
    @DeleteMapping("/{id}/players/{playerId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") int teamId,@PathVariable("playerId") int playerId){
        playerService.delete(playerId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResourceErrorResponse> handleException(ResourceNotFoundException e) {
        ResourceErrorResponse response = new ResourceErrorResponse(
                e.getMessage(),
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
