package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link ObservacaoDoPedido}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas à criação de observações do pedido,
 * como validação do construtor para descrições válidas e inválidas.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Validação do construtor para descrição válida.</li>
 *   <li>Validação do construtor para descrição nula.</li>
 *   <li>Validação do construtor para descrição vazia.</li>
 *   <li>Validação do construtor para descrição composta apenas por espaços.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link ObservacaoDoPedido} para simular o fluxo de uso.</li>
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
class ObservacaoDoPedidoTest {

    @Test
    void testConstrutorComDescricaoValida() {
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem cebola");
        assertEquals("Sem cebola", obs.getDescricao());
    }

    @Test
    void testConstrutorComDescricaoNula() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido(null));
    }

    @Test
    void testConstrutorComDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido(""));
    }

    @Test
    void testConstrutorComDescricaoEspacos() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido("   "));
    }
}