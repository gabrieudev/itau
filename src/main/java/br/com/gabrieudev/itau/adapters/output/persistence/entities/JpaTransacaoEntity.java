package br.com.gabrieudev.itau.adapters.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.itau.domain.Transacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "transacao")
public class JpaTransacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    public Transacao toDomain() {
        return new ModelMapper().map(this, Transacao.class);
    }

    public static JpaTransacaoEntity fromDomain(Transacao transacao) {
        return new ModelMapper().map(transacao, JpaTransacaoEntity.class);
    }
}
