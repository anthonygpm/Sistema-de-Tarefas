package dev.anthony.tarefas.controller;

import dev.anthony.tarefas.custom_messages.ErrorMessage;
import dev.anthony.tarefas.dto.tarefa.TarefaRequestDTO;
import dev.anthony.tarefas.dto.tarefa.TarefaResponseDTO;
import dev.anthony.tarefas.model.Tarefa;
import dev.anthony.tarefas.service.TarefaService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<TarefaResponseDTO>> findAll() {
        return ResponseEntity
                .ok(tarefaService.findAll()
                .stream()
                .map(this::toDTO)
                .toList()); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
            return ResponseEntity.ok(toDTO(tarefaService.findById(id))); // 200
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TarefaRequestDTO dto) {

        var tarefa = new Tarefa ();
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());

        var result = tarefaService.create(tarefa);
        return ResponseEntity
                .status(201)
                .body(toDTO(result)); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid TarefaRequestDTO dto) {

        var dadosAtualizados = new Tarefa();
        dadosAtualizados.setTitulo(dto.titulo());
        dadosAtualizados.setDescricao(dto.descricao());

        var updatedTarefa = tarefaService.update(id, dadosAtualizados);
        return ResponseEntity.ok(toDTO(updatedTarefa));
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<?> completed(@PathVariable UUID id) {
        var completedTarefa = tarefaService.completed(id);
        return ResponseEntity
                .ok(toDTO(completedTarefa)); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        tarefaService.deleteById(id);
        return ResponseEntity
                .status(204)
                .build(); // 204
    }

    private TarefaResponseDTO toDTO(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getConcluida(),
                tarefa.getCriadaEm()
        );
    }
}
