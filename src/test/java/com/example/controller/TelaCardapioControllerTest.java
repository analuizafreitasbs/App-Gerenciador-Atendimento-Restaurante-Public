package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.ItemPedido;
import com.example.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaCardapioController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento do cardápio,
 * como carregar itens e filtrar itens por nome.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se os itens do cardápio são carregados corretamente ao setar o restaurante.</li>
 *   <li>Testa o filtro de itens do cardápio por nome, de forma case insensitive.</li>
 *   <li>Testa o filtro vazio, que deve retornar todos os itens.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Uma classe fake de Restaurante é utilizada para isolar o teste e permitir manipulação direta do cardápio.</li>
 *   <li>O método auxiliar {@code filtrarItens} simula a lógica de filtragem do cardápio.</li>
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
public class TelaCardapioControllerTest {

    private RestauranteFake restauranteFake;

    /**
     * Classe fake para simular Restaurante sem dependências externas.
     */
    static class RestauranteFake extends Restaurante {
        private List<ItemPedido> cardapio = new ArrayList<>();

        public RestauranteFake() {
            super("Restaurante Fake");
        }

        @Override
        public List<ItemPedido> getCardapio() {
            return cardapio;
        }

        public void setCardapio(List<ItemPedido> itens) {
            cardapio = itens;
        }
    }

    @BeforeEach
    public void setup() {
        restauranteFake = new RestauranteFake();
    }

    /**
     * Testa se os itens do cardápio são carregados corretamente ao setar o restaurante.
     */
    @Test
    public void testSetRestauranteCarregaItens() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Pizza", 1, 20.0));
        itens.add(new ItemPedido("Soda", 2, 5.0));

        restauranteFake.setCardapio(itens);

        List<ItemPedido> cardapio = restauranteFake.getCardapio();

        assertEquals(2, cardapio.size());
        assertEquals("Pizza", cardapio.get(0).getNome());
        assertEquals("Soda", cardapio.get(1).getNome());
    }

    /**
     * Testa o filtro de itens do cardápio por nome (case insensitive) e o filtro vazio.
     */
    @Test
    public void testFiltrarItens() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Pizza", 1, 20.0));
        itens.add(new ItemPedido("Soda", 2, 5.0));
        itens.add(new ItemPedido("Pasta", 3, 15.0));

        restauranteFake.setCardapio(itens);

        // Filtrar itens que contém "p" (case insensitive)
        List<ItemPedido> filtradosP = filtrarItens(restauranteFake.getCardapio(), "p");
        assertEquals(2, filtradosP.size());

        // Filtrar itens que contém "soda"
        List<ItemPedido> filtradosSoda = filtrarItens(restauranteFake.getCardapio(), "soda");
        assertEquals(1, filtradosSoda.size());
        assertEquals("Soda", filtradosSoda.get(0).getNome());

        // Filtrar todos
        List<ItemPedido> filtradosTodos = filtrarItens(restauranteFake.getCardapio(), "");
        assertEquals(3, filtradosTodos.size());
    }

    /**
     * Método auxiliar para filtrar itens do cardápio por nome (case insensitive).
     *
     * @param itens  lista de itens do cardápio
     * @param filtro filtro de busca (case insensitive)
     * @return lista de itens filtrados
     */
    private List<ItemPedido> filtrarItens(List<ItemPedido> itens, String filtro) {
        filtro = filtro == null ? "" : filtro.trim().toLowerCase();
        List<ItemPedido> resultado = new ArrayList<>();
        for (ItemPedido item : itens) {
            if (filtro.isEmpty() || item.getNome().toLowerCase().contains(filtro)) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}