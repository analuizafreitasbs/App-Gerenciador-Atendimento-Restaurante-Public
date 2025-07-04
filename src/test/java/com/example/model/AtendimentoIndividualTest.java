package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.TipoCliente;

/**
 * Classe de teste unitário para a lógica de negócio da {@link AtendimentoIndividual}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas à criação e validação de atendimentos individuais.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o construtor lança exceção ao receber cliente nulo.</li>
 *   <li>Verifica se o construtor lança exceção ao receber pedido nulo.</li>
 *   <li>Verifica se o construtor válido associa corretamente o cliente e o pedido.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link Cliente} e {@link Pedido} para simular o fluxo de uso.</li>
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
class AtendimentoIndividualTest {

    private Cliente clienteMock;
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        // Ajuste o construtor conforme sua classe Cliente
        clienteMock = new Cliente(1, "Cliente Teste", TipoCliente.COMUM);
        pedidoMock = new Pedido();
    }

    /**
     * Testa se o construtor lança exceção ao receber cliente nulo.
     */
    @Test
    void construtorDeveLancarExcecaoSeClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoIndividual(null, pedidoMock));
    }

    /**
     * Testa se o construtor lança exceção ao receber pedido nulo.
     */
    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoIndividual(clienteMock, null));
    }

    /**
     * Testa se o construtor válido associa corretamente o cliente e o pedido.
     */
    @Test
    void construtorValidoDeveAssociarClienteEPedido() {
        AtendimentoIndividual atendimento = new AtendimentoIndividual(clienteMock, pedidoMock);
        assertNotNull(atendimento.getCliente());
        assertEquals(clienteMock, atendimento.getCliente());
        assertEquals(pedidoMock, atendimento.getPedido());
    }
}