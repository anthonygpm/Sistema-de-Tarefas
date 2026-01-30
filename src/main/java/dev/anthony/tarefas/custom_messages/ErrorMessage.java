package dev.anthony.tarefas.custom_messages;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Resposta padrão de erro")
public class ErrorMessage {

    @Schema(example = "Tarefa não encontrada.")
    private final String message;

    @Schema(example = "NOT_FOUND")
    private final String type;
}
