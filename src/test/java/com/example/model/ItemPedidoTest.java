package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link ItemPedido}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas à criação e manipulação de itens do pedido,
 * como validação do construtor, adição de observações e cálculo de subtotal.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Validação do construtor para nome, quantidade e preço inválidos.</li>
 *   <li>Adição de observação válida e rejeição de observação nula.</li>
 *   <li>Cálculo correto do subtotal do item.</li>
 *   <li>Verifica se a lista de observações é inicialmente vazia.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link ObservacaoDoPedido} para simular o fluxo de uso do item.</li>
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
class ItemPedidoTest {

    private ItemPedido item;

    @BeforeEach
    void setUp() {
        item = new ItemPedido("Pizza", 2, 30.0);
    }

    @Test
    void testConstrutorValido() {
        assertEquals("Pizza", item.getNome());
        assertEquals(2, item.getQuantidade());
        assertEquals(30.0, item.getPreco());
        assertTrue(item.getObservacoes().isEmpty());
    }

    @Test
    void testConstrutorNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido(null, 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("", 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("   ", 1, 10.0));
    }

    @Test
    void testConstrutorQuantidadeInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", 0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", -1, 10.0));
    }

    @Test
    void testConstrutorPrecoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", 1, -5.0));
    }

    @Test
    void testAdicionarObservacaoValida() {
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem cebola");
        item.adicionarObservacao(obs);
        assertEquals(1, item.getObservacoes().size());
        assertEquals("Sem cebola", item.getObservacoes().get(0).getDescricao());
    }

    @Test
    void testAdicionarObservacaoNula() {
        assertThrows(NullPointerException.class, () -> item.adicionarObservacao(null));
    }

    @Test
    void testCalcularSubtotal() {
        assertEquals(60.0, item.calcularSubtotal(), 0.001);
    }
}