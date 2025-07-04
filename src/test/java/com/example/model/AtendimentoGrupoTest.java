package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de teste unitário para a lógica de negócio da {@link AtendimentoGrupo}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas à criação e validação de atendimentos de grupo.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o construtor lança exceção ao receber grupo nulo.</li>
 *   <li>Verifica se o construtor lança exceção ao receber pedido nulo.</li>
 *   <li>Verifica se o construtor válido associa corretamente o grupo e o pedido.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link GrupoClientes} e {@link Pedido} para simular o fluxo de uso.</li>
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
class AtendimentoGrupoTest {

    private GrupoClientes grupoMock;
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        grupoMock = new GrupoClientes(1, "Grupo Teste");
        pedidoMock = new Pedido();
    }

    /**
     * Testa se o construtor lança exceção ao receber grupo nulo.
     */
    @Test
    void construtorDeveLancarExcecaoSeGrupoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoGrupo(null, pedidoMock));
    }

    /**
     * Testa se o construtor lança exceção ao receber pedido nulo.
     */
    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoGrupo(grupoMock, null));
    }

    /**
     * Testa se o construtor válido associa corretamente o grupo e o pedido.
     */
    @Test
    void construtorValidoDeveAssociarGrupoEPedido() {
        AtendimentoGrupo atendimento = new AtendimentoGrupo(grupoMock, pedidoMock);
        assertNotNull(atendimento.getGrupo());
        assertEquals(grupoMock, atendimento.getGrupo());
        assertEquals(pedidoMock, atendimento.getPedido());
    }
}