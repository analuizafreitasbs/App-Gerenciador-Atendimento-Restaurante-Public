package com.example.model;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.Status;

/**
 * Classe de teste unitário para a lógica de negócio da {@link Atendimento}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao ciclo de vida do atendimento,
 * como validação do construtor, início, finalização e cálculo de tempo.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o construtor lança exceção ao receber pedido nulo.</li>
 *   <li>Verifica se iniciar atendimento lança exceção se a hora de chegada for nula.</li>
 *   <li>Verifica se finalizar atendimento lança exceção se não iniciado.</li>
 *   <li>Verifica se calcular tempo total lança exceção se não finalizado.</li>
 *   <li>Testa o ciclo completo de atendimento: início, finalização e cálculo de tempos.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Uma classe concreta interna é usada para testar a classe abstrata {@link Atendimento}.</li>
 * </ul>
 *
 * @author Ana Luiza Freitas Brito Siqueira
 * @author Bruno Campos Penha
 * @author Grazielly de Sousa Barros
 * @author João Gabriel Oliveira Magalhães
 * @author João Vitor Moreira Lemos
 * @author Robert Alves Guimarães
 * @author Vinicius D’Oliveira Rocha
 * @version 1.0
 */
class AtendimentoTest {

    private Pedido pedidoMock;

    // Classe concreta para testar a classe abstrata Atendimento
    static class AtendimentoConcreto extends Atendimento {
        public AtendimentoConcreto(Pedido pedido) {
            super(pedido);
        }
    }

    @BeforeEach
    void setUp() {
        pedidoMock = new Pedido();
    }

    /**
     * Testa se o construtor lança exceção ao receber pedido nulo.
     */
    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoConcreto(null));
    }

    /**
     * Testa se iniciar atendimento lança exceção se a hora de chegada for nula.
     */
    @Test
    void iniciarAtendimentoDeveLancarExcecaoSeHoraChegadaNula() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        assertThrows(NullPointerException.class, () -> atendimento.iniciarAtendimento(null));
    }

    /**
     * Testa se finalizar atendimento lança exceção se não iniciado.
     */
    @Test
    void finalizarAtendimentoDeveLancarExcecaoSeNaoIniciado() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        assertThrows(NullPointerException.class, atendimento::finalizarAtendimento);
    }

    /**
     * Testa se calcular tempo total lança exceção se não finalizado.
     */
    @Test
    void calcularTempoTotalDeveLancarExcecaoSeNaoFinalizado() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        LocalTime chegada = LocalTime.now().minusMinutes(10);
        atendimento.iniciarAtendimento(chegada);
        assertThrows(NullPointerException.class, atendimento::calcularTempoTotal);
    }

    /**
     * Testa o ciclo completo de atendimento: início, finalização e cálculo de tempos.
     */
    @Test
    void cicloCompletoAtendimento() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        LocalTime chegada = LocalTime.now().minusMinutes(15);
        atendimento.iniciarAtendimento(chegada);

        assertEquals(Status.EM_ATENDIMENTO, atendimento.getStatus());
        assertNotNull(atendimento.getInicio());
        assertNotNull(atendimento.getTempoDeEspera());
        assertTrue(atendimento.getTempoDeEspera().toMinutes() >= 0);

        atendimento.finalizarAtendimento();

        assertEquals(Status.FINALIZADO, atendimento.getStatus());
        assertNotNull(atendimento.getFim());
        assertNotNull(atendimento.getTempoDeAtendimento());
        assertTrue(atendimento.getTempoDeAtendimento().toMillis() >= 0);

        Duration total = atendimento.calcularTempoTotal();
        assertNotNull(total);
        assertTrue(total.toMillis() >= 0);
    }
}