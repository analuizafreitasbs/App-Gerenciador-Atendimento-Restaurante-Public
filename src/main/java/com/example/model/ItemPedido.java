package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um item de pedido no restaurante.
 * <p>
 * Armazena informações como nome, quantidade, preço e observações do item.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar um item com nome nulo ou vazio, quantidade menor que 1 ou preço negativo.</li>
 *   <li>{@link NullPointerException} - Lançada ao adicionar uma observação nula.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar um item de pedido com nome, quantidade e preço válidos.</li>
 *   <li>Adicionar observações conforme necessário.</li>
 *   <li>Calcular o subtotal do item.</li>
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
public class ItemPedido {
    private String nome;
    private int quantidade;
    private double preco;
    private List<ObservacaoDoPedido> observacoes;

    /**
     * Construtor do ItemPedido.
     *
     * @param nome      nome do item (não pode ser nulo ou vazio)
     * @param quantidade quantidade do item (deve ser maior que zero)
     * @param preco     preço unitário do item (não pode ser negativo)
     * @throws IllegalArgumentException se nome for nulo/vazio, quantidade menor que 1 ou preço negativo
     */
    public ItemPedido(String nome, int quantidade, double preco) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do item não pode ser nulo ou vazio.");
        if (quantidade < 1) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo.");
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.observacoes = new ArrayList<>();
    }

    /**
     * Adiciona uma observação ao item do pedido.
     *
     * @param obs observação a ser adicionada (não pode ser nula)
     * @throws NullPointerException se a observação for nula
     */
    public void adicionarObservacao(ObservacaoDoPedido obs) {
        if (obs == null) throw new NullPointerException("Observação não pode ser nula.");
        observacoes.add(obs);
    }

    /**
     * Calcula o subtotal do item (quantidade x preço).
     *
     * @return subtotal do item
     */
    public double calcularSubtotal() {
        return quantidade * preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public List<ObservacaoDoPedido> getObservacoes() {
        return observacoes;
    }
}

