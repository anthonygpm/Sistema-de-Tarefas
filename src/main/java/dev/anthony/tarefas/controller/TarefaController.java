package dev.anthony.tarefas.controller;

import dev.anthony.tarefas.custom_messages.ErrorMessage;
import dev.anthony.tarefas.dto.tarefa.TarefaRequestDTO;
import dev.anthony.tarefas.dto.tarefa.TarefaResponseDTO;
import dev.anthony.tarefas.model.Tarefa;
import dev.anthony.tarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar tarefas")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de tarefas",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = TarefaResponseDTO.class)
                    )
            )
    )
    public ResponseEntity<List<TarefaResponseDTO>> findAll() {
        return ResponseEntity
                .ok(tarefaService.findAll()
                        .stream()
                        .map(this::toDTO)
                        .toList()); // 200
    }



    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa pelo ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarefa encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable UUID id) {
            return ResponseEntity.ok(toDTO(tarefaService.findById(id))); // 200
    }


    @PostMapping
    @Operation(summary = "Criar tarefa")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Tarefa criada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponseDTO> create(@RequestBody @Valid TarefaRequestDTO dto) {

        var tarefa = new Tarefa ();
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());

        var result = tarefaService.create(tarefa);
        return ResponseEntity
                .status(201)
                .body(toDTO(result)); // 201
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar tarefa",
            description = "Atualiza o título e a descrição de uma tarefa."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarefa atualizada.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid TarefaRequestDTO dto) {

        var dadosAtualizados = new Tarefa();
        dadosAtualizados.setTitulo(dto.titulo());
        dadosAtualizados.setDescricao(dto.descricao());

        var updatedTarefa = tarefaService.update(id, dadosAtualizados);
        return ResponseEntity.ok(toDTO(updatedTarefa));
    }


    @PatchMapping("/{id}/completed")
    @Operation(summary = "Completar tarefa")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarefa completa.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponseDTO> completed(@PathVariable UUID id) {
        var completedTarefa = tarefaService.completed(id);
        return ResponseEntity
                .ok(toDTO(completedTarefa)); // 200
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar tarefa",
            description = "Deleta a tarefa de acordo com o ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa deletada."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
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
