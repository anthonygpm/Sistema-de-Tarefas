package dev.anthony.tarefas.controller;

import dev.anthony.tarefas.custom_messages.ErrorMessage;
import dev.anthony.tarefas.model.Tarefa;
import dev.anthony.tarefas.service.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<Tarefa>> findAll() {
        return ResponseEntity.ok(tarefaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        try {
            var tarefa = tarefaService.findById(id);
            return ResponseEntity.ok(tarefa); // 200
        }
        catch(RuntimeException e) {
            var errorMessage = new ErrorMessage(e.getMessage(), "NOT_FOUND");
            return ResponseEntity.status(404).body(errorMessage); // 404
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Tarefa tarefa) {

        try {
            var result = tarefaService.create(tarefa);
            return ResponseEntity.ok(result); // 200
        }
        catch(IllegalArgumentException e) {
            var errorMessage = new ErrorMessage(e.getMessage(), "INVALID_INPUT");
            return ResponseEntity.badRequest().body(errorMessage); // 400
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        try {
            tarefaService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> update(@PathVariable UUID id, @RequestBody Tarefa dadosAtualizados) {
        try {
            return ResponseEntity.ok(tarefaService.update(id, dadosAtualizados));
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<?> completed(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(tarefaService.completed(id));
        }
        catch (RuntimeException e) {
            var errorMessage = new ErrorMessage(e.getMessage(), "NOT_FOUND");
            return ResponseEntity.status(404).body(errorMessage); // 404
        }
    }
}
