package dev.anthony.tarefas.dto.tarefa;

import java.time.LocalDateTime;
import java.util.UUID;

public record TarefaResponseDTO(
        UUID id,
        String titulo,
        String descricao,
        Boolean concluida,
        LocalDateTime criadaEm
) {}
