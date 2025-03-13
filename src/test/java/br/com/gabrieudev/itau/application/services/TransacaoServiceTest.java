package br.com.gabrieudev.itau.application.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gabrieudev.itau.application.exceptions.InternalErrorException;
import br.com.gabrieudev.itau.application.exceptions.NotFoundException;
import br.com.gabrieudev.itau.application.ports.output.TransacaoOutputPort;
import br.com.gabrieudev.itau.domain.Estatistica;
import br.com.gabrieudev.itau.domain.Transacao;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoOutputPort transacaoOutputPort;

    @InjectMocks
    private TransacaoService transacaoService;

    @Captor
    private ArgumentCaptor<LocalDateTime> localDateTimeArgumentCaptor;

    private Transacao transacao;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        transacao = new Transacao(null, BigDecimal.valueOf(100), LocalDateTime.now());
    }

    @Test
    void create_QuandoTransacaoCriada_RetornaTransacao() {
        when(transacaoOutputPort.create(transacao)).thenReturn(Optional.of(transacao));

        Transacao resultado = transacaoService.create(transacao);

        assertEquals(transacao, resultado);
        verify(transacaoOutputPort).create(transacao);
    }

    @Test
    void create_QuandoFalhaCriacao_LancaExcecao() {
        when(transacaoOutputPort.create(transacao)).thenReturn(Optional.empty());

        assertThrows(InternalErrorException.class, () -> transacaoService.create(transacao));
        verify(transacaoOutputPort).create(transacao);
    }

    @Test
    void delete_QuandoTransacaoNaoExiste_LancaExcecao() {
        when(transacaoOutputPort.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> transacaoService.delete(id));
        verify(transacaoOutputPort).existsById(id);
        verify(transacaoOutputPort, never()).delete(id);
    }

    @Test
    void delete_QuandoFalhaExclusao_LancaExcecao() {
        when(transacaoOutputPort.existsById(id)).thenReturn(true);
        when(transacaoOutputPort.delete(id)).thenReturn(false);

        assertThrows(InternalErrorException.class, () -> transacaoService.delete(id));
        verify(transacaoOutputPort).delete(id);
    }

    @Test
    void delete_QuandoExclusaoBemSucedida_NaoLancaExcecao() {
        when(transacaoOutputPort.existsById(id)).thenReturn(true);
        when(transacaoOutputPort.delete(id)).thenReturn(true);

        assertDoesNotThrow(() -> transacaoService.delete(id));
        verify(transacaoOutputPort).delete(id);
    }

    @Test
    void getEstatistica_QuandoEncontrada_RetornaEstatistica() {
        int seconds = 60;
        Estatistica estatistica = new Estatistica();
        when(transacaoOutputPort.getStatistics(localDateTimeArgumentCaptor.capture())).thenReturn(Optional.of(estatistica));

        Estatistica resultado = transacaoService.getStatistics(seconds);

        assertEquals(estatistica, resultado);
        LocalDateTime dataEsperada = LocalDateTime.now().minusSeconds(seconds);
        LocalDateTime dataCapturada = localDateTimeArgumentCaptor.getValue();
        assertTrue(dataCapturada.isAfter(dataEsperada.minusSeconds(1)) && dataCapturada.isBefore(dataEsperada.plusSeconds(1)));
    }

    @Test
    void getEstatistica_QuandoNaoEncontrada_LancaExcecao() {
        int seconds = 60;
        when(transacaoOutputPort.getStatistics(any(LocalDateTime.class))).thenReturn(Optional.empty());

        assertThrows(InternalErrorException.class, () -> transacaoService.getStatistics(seconds));
    }

    @Test
    void getAll_QuandoChamado_RetornaListaTransacoes() {
        int page = 0;
        int size = 10;
        List<Transacao> transacoes = List.of(transacao);
        when(transacaoOutputPort.getAll(page, size)).thenReturn(transacoes);

        List<Transacao> resultado = transacaoService.getAll(page, size);

        assertEquals(transacoes, resultado);
        verify(transacaoOutputPort).getAll(page, size);
    }
}