package dev.anthony.tarefas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gerenciamento de Tarefas",
                version = "1.0",
                description = "API RESTful para gerenciar tarefas, permitindo criar, listar, atualizar e excluir tarefas."
        )
)

public class OpenApiConfig {
}
