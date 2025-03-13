package br.com.gabrieudev.itau.application.ports.input;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.itau.domain.Estatistica;
import br.com.gabrieudev.itau.domain.Transacao;

public interface TransacaoInputPort {
    Transacao create(Transacao transacao);

    void delete(UUID id);

    Estatistica getStatistics(Integer seconds);

    List<Transacao> getAll(Integer page, Integer size);
}
