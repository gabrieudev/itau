package br.com.gabrieudev.itau.adapters.input.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.itau.adapters.input.rest.dtos.CreateTransacaoDTO;
import br.com.gabrieudev.itau.adapters.input.rest.dtos.EstatisticaDTO;
import br.com.gabrieudev.itau.adapters.input.rest.dtos.TransacaoDTO;
import br.com.gabrieudev.itau.application.exceptions.StandardException;
import br.com.gabrieudev.itau.application.services.TransacaoService;
import br.com.gabrieudev.itau.domain.Transacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@Slf4j
public class TransacaoController {
    private final TransacaoService transacaoService;
    
    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(
        summary = "Cria uma nova transação",
        description = "Cria uma nova transação com base nos dados fornecidos",
        tags = {"Transação"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Transação criada com sucesso"
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Dados inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            )
        }
    )
    @PostMapping("/transacao")
    public ResponseEntity<TransacaoDTO> create(
        @Valid
        @RequestBody
        CreateTransacaoDTO createTransacaoDTO,

        HttpServletRequest request
    ) {
        log.info("POST /api/v1/transacao | Client: {}", request.getRemoteAddr());

        Transacao transacao = transacaoService.create(createTransacaoDTO.toDomain());

        return ResponseEntity.status(HttpStatus.CREATED).body(TransacaoDTO.fromDomain(transacao));
    }

    @Operation(
        summary = "Deleta uma transação",
        description = "Deleta uma transação com base no id fornecido",
        tags = {"Transação"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Transação deletada com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Transação não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            )
        }
    )
    @DeleteMapping("/transacao/{id}")
    public ResponseEntity<Void> delete(
        @Schema(
            description = "Id da transação",
            example = "123e4567-e89b-12d3-a456-426655440000"
        )
        @PathVariable UUID id,

        HttpServletRequest request
    ) {
        log.info("DELETE /api/v1/transacao/{} | Client: {}", id, request.getRemoteAddr());

        transacaoService.delete(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "Obtém estatísticas das transações",
        description = "Obtém estatísticas das transações realizadas",
        tags = {"Transação"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Estatísticas obtidas com sucesso"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            )
        }
    )
    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDTO> getEstatistica(
        @Schema(
            description = "Quantidade de segundos para calcular as estatísticas",
            example = "60"
        )
        @RequestParam(required = true) Integer segundos,

        HttpServletRequest request
    ) {
        log.info("GET /api/v1/estatistica?segundos={} | Client: {}", segundos, request.getRemoteAddr());

        return ResponseEntity.status(HttpStatus.OK).body(EstatisticaDTO.fromDomain(transacaoService.getStatistics(segundos)));
    }

    @Operation(
        summary = "Obtém todas as transações",
        description = "Obtém todas as transações",
        tags = {"Transação"}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Transações obtidas com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransacaoDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardException.class)
                )
            )
        }
    )
    @GetMapping("/transacao")
    public ResponseEntity<Page<TransacaoDTO>> getAll(
        @Schema(
            description = "Número da página",
            example = "0"
        )
        @RequestParam(required = true) Integer page,
        
        @Schema(
            description = "Quantidade de transações por página",
            example = "10"
        )
        @RequestParam(required = true) Integer size,

        HttpServletRequest request
    ) {
        log.info("GET /api/v1/transacao?page={}&size={} | Client: {}", page, size, request.getRemoteAddr());

        List<TransacaoDTO> transacoes = transacaoService.getAll(page, size)
            .stream()
            .map(TransacaoDTO::fromDomain)
            .toList();
        
        Page<TransacaoDTO> transacoesPage = new PageImpl<>(transacoes, PageRequest.of(page, size), transacoes.size());

        return ResponseEntity.status(HttpStatus.OK).body(transacoesPage);
    }
}
