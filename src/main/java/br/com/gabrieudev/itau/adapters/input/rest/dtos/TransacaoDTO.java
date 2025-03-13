package br.com.gabrieudev.itau.adapters.input.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.itau.domain.Transacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {
    @Schema(
        description = "Id da transação",
        example = "123e4567-e89b-12d3-a456-426655440000"
    )
    private UUID id;
    
    @Schema(
        description = "Valor da transação",
        example = "100.00"
    )
    private BigDecimal valor;
    
    @Schema(
        description = "Data e hora da transação",
        example = "2022-01-01T00:00:00Z"
    )
    private LocalDateTime dataHora;

    public Transacao toDomain() {
        return new ModelMapper().map(this, Transacao.class);
    }

    public static TransacaoDTO fromDomain(Transacao transacao) {
        return new ModelMapper().map(transacao, TransacaoDTO.class);
    }
}
