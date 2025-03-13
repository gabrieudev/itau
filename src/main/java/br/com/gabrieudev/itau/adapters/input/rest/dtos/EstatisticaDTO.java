package br.com.gabrieudev.itau.adapters.input.rest.dtos;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.itau.domain.Estatistica;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaDTO {
    @Schema(
        description = "Quantidade de transações dos últimos 60 segundos",
        example = "5"
    )
    private Integer count;

    @Schema(
        description = "Soma das transações dos últimos 60 segundos",
        example = "100.00"
    )
    private BigDecimal sum;
    
    @Schema(
        description = "Média das transações dos últimos 60 segundos",
        example = "20.00"
    )
    private BigDecimal avg;
    
    @Schema(
        description = "Menor valor das transações dos últimos 60 segundos",
        example = "10.00"
    )
    private BigDecimal min;
    
    @Schema(
        description = "Maior valor das transações dos últimos 60 segundos",
        example = "30.00"
    )
    private BigDecimal max;

    public static EstatisticaDTO fromDomain(Estatistica estatistica) {
        return new ModelMapper().map(estatistica, EstatisticaDTO.class);
    }
}
