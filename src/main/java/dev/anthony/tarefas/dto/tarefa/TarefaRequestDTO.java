package dev.anthony.tarefas.dto.tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TarefaRequestDTO(

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
        String titulo,

        @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
        String descricao
) {}
