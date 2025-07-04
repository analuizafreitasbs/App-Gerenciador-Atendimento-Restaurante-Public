package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de teste unitário para a lógica de negócio da {@link Cardapio}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento do cardápio,
 * como adição e busca de itens.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Não permite adicionar item nulo ao cardápio.</li>
 *   <li>Adiciona item corretamente e permite buscá-lo pelo nome.</li>
 *   <li>Retorna null ao buscar item inexistente.</li>
 *   <li>A busca de item não diferencia maiúsculas de minúsculas.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link ItemPedido} para simular o fluxo de uso do cardápio.</li>
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
class CardapioTest {

    private Cardapio cardapio;

    @BeforeEach
    void setUp() {
        cardapio = new Cardapio();
    }

    /**
     * Testa se não é permitido adicionar item nulo ao cardápio.
     */
    @Test
    void adicionarItemNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cardapio.adicionarItem(null));
    }

    /**
     * Testa se adicionar item adiciona corretamente e permite buscá-lo pelo nome.
     */
    @Test
    void adicionarItemAdicionaCorretamente() {
        ItemPedido item = new ItemPedido("Pizza", 1, 30.0);
        cardapio.adicionarItem(item);
        assertEquals(item, cardapio.buscarItem("Pizza"));
    }

    /**
     * Testa se buscar item retorna null se o item não existir.
     */
    @Test
    void buscarItemRetornaNullSeNaoExistir() {
        assertNull(cardapio.buscarItem("Inexistente"));
    }

    /**
     * Testa se a busca de item não diferencia maiúsculas de minúsculas.
     */
    @Test
    void buscarItemNaoDiferenciaMaiusculasMinusculas() {
        ItemPedido item = new ItemPedido("Suco", 1, 8.0);
        cardapio.adicionarItem(item);
        assertNotNull(cardapio.buscarItem("suco"));
        assertNotNull(cardapio.buscarItem("SUCO"));
    }
}