package br.com.gabrieudev.itau.adapters.input.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.itau.domain.Transacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransacaoDTO {
    @Schema(
        description = "Valor da transação",
        example = "100.00"
    )
    @NotNull(message = "O valor da transação é obrigatório")
    @PositiveOrZero(message = "O valor da transação deve ser maior ou igual a zero")
    private BigDecimal valor;
    
    @Schema(
        description = "Data e hora da transação",
        example = "2022-01-01T00:00:00Z"
    )
    @NotNull(message = "A data e hora da transação é obrigatória")
    @PastOrPresent(message = "A data e hora da transação não pode ser futura")
    private LocalDateTime dataHora;

    public Transacao toDomain() {
        return new ModelMapper().map(this, Transacao.class);
    }
}
