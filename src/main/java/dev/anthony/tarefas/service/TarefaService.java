package dev.anthony.tarefas.service;

import dev.anthony.tarefas.exception.BadRequestException;
import dev.anthony.tarefas.exception.ResourceNotFoundException;
import dev.anthony.tarefas.model.Tarefa;
import dev.anthony.tarefas.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public Tarefa findById(UUID id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));
    }

    public Tarefa create(Tarefa tarefa) {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isBlank()) {
            throw new BadRequestException("Título é obrigatório.");
        }

        return tarefaRepository.save(tarefa);
    }

    public void deleteById(UUID id) {

        if (!tarefaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarefa não encontrada.");
        }

        tarefaRepository.deleteById(id);
    }

    public Tarefa update(UUID id, Tarefa dadosAtualizados) {
        Tarefa tarefa = findById(id);
        tarefa.setTitulo(dadosAtualizados.getTitulo());
        tarefa.setDescricao(dadosAtualizados.getDescricao());

        return tarefaRepository.save(tarefa);
    }

    public Tarefa completed(UUID id) {
        Tarefa tarefa = findById(id);
        tarefa.setConcluida(true);
        return tarefaRepository.save(tarefa);
    }

}
