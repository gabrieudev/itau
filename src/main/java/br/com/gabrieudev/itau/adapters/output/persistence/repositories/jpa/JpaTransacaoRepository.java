package br.com.gabrieudev.itau.adapters.output.persistence.repositories.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.itau.adapters.output.persistence.entities.JpaTransacaoEntity;
import br.com.gabrieudev.itau.domain.Estatistica;

@Repository
public interface JpaTransacaoRepository extends JpaRepository<JpaTransacaoEntity, UUID> {
    @Query("SELECT NEW br.com.gabrieudev.itau.domain.Estatistica(" +
          "COUNT(t), " +
          "COALESCE(SUM(t.valor), 0), " +
          "COALESCE(CAST(AVG(t.valor) AS java.math.BigDecimal), 0), " +
          "COALESCE(MIN(t.valor), 0), " +
          "COALESCE(MAX(t.valor), 0)) " +
          "FROM JpaTransacaoEntity t " +
          "WHERE t.dataHora >= :date")
    Estatistica getStatistics(@Param("date") LocalDateTime date);

}

