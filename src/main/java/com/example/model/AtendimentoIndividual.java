package com.example.model;

/**
 * Classe que representa um atendimento realizado para um cliente individual no restaurante.
 * <p>
 * Estende {@link Atendimento} e associa um {@link Cliente} ao atendimento.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada no construtor caso o cliente seja nulo.</li>
 *   <li>{@link IllegalArgumentException} - Lançada no construtor da superclasse caso o pedido seja nulo.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar passando um {@link Cliente} e um {@link Pedido} válidos.</li>
 *   <li>Utilizar métodos herdados para controlar o ciclo de vida do atendimento.</li>
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
public class AtendimentoIndividual extends Atendimento {
    private Cliente cliente;

    /**
     * Construtor do AtendimentoIndividual.
     *
     * @param cliente o cliente atendido (não pode ser nulo)
     * @param pedido  o pedido associado ao atendimento (não pode ser nulo)
     * @throws IllegalArgumentException se o cliente ou o pedido forem nulos
     */
    public AtendimentoIndividual(Cliente cliente, Pedido pedido) {
        super(pedido);
        if (cliente == null) throw new IllegalArgumentException("Cliente não pode ser nulo.");
        this.cliente = cliente;
    }

    /**
     * Retorna o cliente associado a este atendimento.
     *
     * @return cliente atendido
     */
    public Cliente getCliente() {
        return cliente;
    }
}