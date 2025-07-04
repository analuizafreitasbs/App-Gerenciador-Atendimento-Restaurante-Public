package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o cardápio do restaurante.
 * <p>
 * Responsável por armazenar e gerenciar os itens disponíveis para pedido.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link NullPointerException} - Lançada ao tentar adicionar um item nulo ao cardápio.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar o cardápio.</li>
 *   <li>Adicionar itens usando {@link #adicionarItem(ItemPedido)}.</li>
 *   <li>Buscar itens pelo nome usando {@link #buscarItem(String)}.</li>
 * </ol>
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
public class Cardapio {
    private List<ItemPedido> itens;

    /**
     * Construtor do Cardápio.
     * Inicializa a lista de itens.
     */
    public Cardapio() {
        this.itens = new ArrayList<>();
    }

    /**
     * Adiciona um item ao cardápio.
     *
     * @param item o item a ser adicionado (não pode ser nulo)
     * @throws NullPointerException se o item for nulo
     */
    public void adicionarItem(ItemPedido item) {
        if (item == null) throw new NullPointerException("Item do cardápio não pode ser nulo.");
        itens.add(item);
    }

    /**
     * Busca um item pelo nome no cardápio.
     *
     * @param nome nome do item a ser buscado
     * @return o item encontrado ou {@code null} se não existir
     */
    public ItemPedido buscarItem(String nome) {
        return itens.stream().filter(i -> i.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }
}
