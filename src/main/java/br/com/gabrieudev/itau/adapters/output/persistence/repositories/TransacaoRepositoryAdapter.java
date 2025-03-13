package br.com.gabrieudev.itau.adapters.output.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.itau.adapters.output.persistence.entities.JpaTransacaoEntity;
import br.com.gabrieudev.itau.adapters.output.persistence.repositories.jpa.JpaTransacaoRepository;
import br.com.gabrieudev.itau.application.ports.output.TransacaoOutputPort;
import br.com.gabrieudev.itau.domain.Estatistica;
import br.com.gabrieudev.itau.domain.Transacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransacaoRepositoryAdapter implements TransacaoOutputPort {
    private final JpaTransacaoRepository jpaTransacaoRepository;

    public TransacaoRepositoryAdapter(JpaTransacaoRepository jpaTransacaoRepository) {
        this.jpaTransacaoRepository = jpaTransacaoRepository;
    }

    @Override
    public Optional<Transacao> create(Transacao transacao) {
        try {
            JpaTransacaoEntity jpaEntity = JpaTransacaoEntity.fromDomain(transacao);

            log.info("Creating transacao: {}", jpaEntity);
            return Optional.of(jpaTransacaoRepository.save(jpaEntity).toDomain());
        } catch (Exception e) {
            log.error("Failed to create transacao: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Boolean delete(UUID id) {
        log.info("Deleting transacao: {}", id);
        try {
            jpaTransacaoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Failed to delete transacao: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<Transacao> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return jpaTransacaoRepository.findAll(pageable)
                .stream()
                .map(JpaTransacaoEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Estatistica> getStatistics(LocalDateTime dueDate) {
        try {
            return Optional.of(jpaTransacaoRepository.getStatistics(dueDate));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsById(UUID id) {
        return jpaTransacaoRepository.existsById(id);
    }
}
