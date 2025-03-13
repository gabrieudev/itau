package br.com.gabrieudev.itau.application.ports.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.gabrieudev.itau.domain.Estatistica;
import br.com.gabrieudev.itau.domain.Transacao;

public interface TransacaoOutputPort {
    Optional<Transacao> create(Transacao transacao);

    Boolean delete(UUID id);

    Optional<Estatistica> getStatistics(LocalDateTime date);

    List<Transacao> getAll(Integer page, Integer size);

    Boolean existsById(UUID id);
}
