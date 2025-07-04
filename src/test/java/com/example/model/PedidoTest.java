package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link Pedido}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de pedidos,
 * como geração de ID único, adição e remoção de itens, validação de itens nulos e cálculo do total.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Geração de ID único para cada pedido.</li>
 *   <li>Adição de item ao pedido e verificação da lista de itens.</li>
 *   <li>Não permite adicionar item nulo ao pedido.</li>
 *   <li>Remoção de item do pedido e verificação da lista de itens.</li>
 *   <li>Não permite remover item nulo do pedido.</li>
 *   <li>Cálculo correto do total do pedido com e sem itens.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link ItemPedido} para simular o fluxo de uso do pedido.</li>
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
class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    void testIdUnico() {
        Pedido p2 = new Pedido();
        assertNotEquals(pedido.getId(), p2.getId());
    }

    @Test
    void testAdicionarItem() {
        ItemPedido item = new ItemPedido("Pizza", 2, 30.0);
        pedido.adicionarItem(item);
        assertEquals(1, pedido.getItens().size());
        assertEquals(item, pedido.getItens().get(0));
    }

    @Test
    void testAdicionarItemNulo() {
        assertThrows(NullPointerException.class, () -> pedido.adicionarItem(null));
    }

    @Test
    void testRemoverItem() {
        ItemPedido item = new ItemPedido("Pizza", 2, 30.0);
        pedido.adicionarItem(item);
        pedido.removerItem(item);
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    void testRemoverItemNulo() {
        assertThrows(NullPointerException.class, () -> pedido.removerItem(null));
    }

    @Test
    void testCalcularTotal() {
        pedido.adicionarItem(new ItemPedido("Pizza", 2, 30.0)); // 60.0
        pedido.adicionarItem(new ItemPedido("Suco", 1, 8.0));   // 8.0
        assertEquals(68.0, pedido.calcularTotal(), 0.001);
    }

    @Test
    void testCalcularTotalSemItens() {
        assertEquals(0.0, pedido.calcularTotal(), 0.001);
    }
}