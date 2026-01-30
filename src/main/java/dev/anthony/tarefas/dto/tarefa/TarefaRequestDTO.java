package dev.anthony.tarefas.dto.tarefa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação de uma tarefa")
public record TarefaRequestDTO(

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
        @Schema(
                description = "Título da tarefa",
                example = "Estudar Spring Boot"
        )
        String titulo,

        @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
        @Schema(
                description = "Descrição da tarefa",
                example = "Estudar Swagger e OpenAPI para documentação de APIs RESTful"
        )
        String descricao
) {}
