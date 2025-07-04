package com.example.model;

/**
 * Classe que representa uma observação associada a um item de pedido no restaurante.
 * <p>
 * Utilizada para registrar detalhes ou instruções especiais sobre o item do pedido.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar uma observação com descrição nula ou vazia.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar uma observação com uma descrição válida.</li>
 *   <li>Associar a observação a um {@link ItemPedido}.</li>
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
public class ObservacaoDoPedido {
    private String descricao;

    /**
     * Construtor da ObservacaoDoPedido.
     *
     * @param descricao descrição da observação (não pode ser nula ou vazia)
     * @throws IllegalArgumentException se a descrição for nula ou vazia
     */
    public ObservacaoDoPedido(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da observação não pode ser nula ou vazia.");
        }
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição da observação.
     *
     * @return descrição da observação
     */
    public String getDescricao() {
        return descricao;
    }
}