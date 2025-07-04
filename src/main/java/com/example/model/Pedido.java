package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um pedido realizado no restaurante.
 * <p>
 * Armazena uma lista de itens do pedido e fornece métodos para manipulação e cálculo do valor total.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link NullPointerException} - Lançada ao tentar adicionar ou remover um item nulo.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar um pedido.</li>
 *   <li>Adicionar ou remover itens usando {@link #adicionarItem(ItemPedido)} e {@link #removerItem(ItemPedido)}.</li>
 *   <li>Calcular o valor total do pedido.</li>
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
public class Pedido {
    private static int contador = 0;
    private int id;
    private List<ItemPedido> itens;

    /**
     * Construtor do Pedido.
     * Inicializa o pedido com um identificador único e uma lista vazia de itens.
     */
    public Pedido() {
        this.id = ++contador;
        this.itens = new ArrayList<>();
    }

    /**
     * Retorna o identificador do pedido.
     *
     * @return id do pedido
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a lista de itens do pedido.
     *
     * @return lista de itens
     */
    public List<ItemPedido> getItens() {
        return itens;
    }

    /**
     * Adiciona um item ao pedido.
     *
     * @param item item a ser adicionado (não pode ser nulo)
     * @throws NullPointerException se o item for nulo
     */
    public void adicionarItem(ItemPedido item) {
        if (item == null) throw new NullPointerException("Item do pedido não pode ser nulo.");
        itens.add(item);
    }

    /**
     * Remove um item do pedido.
     *
     * @param item item a ser removido (não pode ser nulo)
     * @throws NullPointerException se o item for nulo
     */
    public void removerItem(ItemPedido item) {
        if (item == null) throw new NullPointerException("Item do pedido não pode ser nulo.");
        itens.remove(item);
    }

    /**
     * Calcula o valor total do pedido somando o subtotal de todos os itens.
     *
     * @return valor total do pedido
     */
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::calcularSubtotal)
                .sum();
    }
}
