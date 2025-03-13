package br.com.gabrieudev.itau.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.itau.application.exceptions.InternalErrorException;
import br.com.gabrieudev.itau.application.exceptions.NotFoundException;
import br.com.gabrieudev.itau.application.ports.input.TransacaoInputPort;
import br.com.gabrieudev.itau.application.ports.output.TransacaoOutputPort;
import br.com.gabrieudev.itau.domain.Estatistica;
import br.com.gabrieudev.itau.domain.Transacao;

public class TransacaoService implements TransacaoInputPort {
    private final TransacaoOutputPort transacaoOutputPort;

    public TransacaoService(TransacaoOutputPort transacaoOutputPort) {
        this.transacaoOutputPort = transacaoOutputPort;
    }

    @Override
    public Transacao create(Transacao transacao) {
        return transacaoOutputPort.create(transacao)
                .orElseThrow(() -> new InternalErrorException("Não foi possível criar a transação"));
    }

    @Override
    public void delete(UUID id) {
        if (!transacaoOutputPort.existsById(id)) {
            throw new NotFoundException("Transação não encontrada");
        }

        if (!transacaoOutputPort.delete(id)) {
            throw new InternalErrorException("Não foi possível deletar a transação");
        }
    }

    @Override
    public Estatistica getStatistics(Integer seconds) {
        LocalDateTime date = LocalDateTime.now().minusSeconds(seconds);
        
        return transacaoOutputPort.getStatistics(date)
                .orElseThrow(() -> new InternalErrorException("Não foi possível obter as estatísticas"));
    }

    @Override
    public List<Transacao> getAll(Integer page, Integer size) {
        return transacaoOutputPort.getAll(page, size);
    }
}
