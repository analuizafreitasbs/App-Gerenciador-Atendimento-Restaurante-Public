package com.example.model;

/**
 * Classe que representa um atendimento realizado para um grupo de clientes no restaurante.
 * <p>
 * Estende {@link Atendimento} e associa um {@link GrupoClientes} ao atendimento.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada no construtor caso o grupo seja nulo.</li>
 *   <li>{@link IllegalArgumentException} - Lançada no construtor da superclasse caso o pedido seja nulo.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar passando um {@link GrupoClientes} e um {@link Pedido} válidos.</li>
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
public class AtendimentoGrupo extends Atendimento {
    private GrupoClientes grupo;

    /**
     * Construtor do AtendimentoGrupo.
     *
     * @param grupo  o grupo de clientes atendido (não pode ser nulo)
     * @param pedido o pedido associado ao atendimento (não pode ser nulo)
     * @throws IllegalArgumentException se o grupo ou o pedido forem nulos
     */
    public AtendimentoGrupo(GrupoClientes grupo, Pedido pedido) {
        super(pedido);
        if (grupo == null) throw new IllegalArgumentException("Grupo não pode ser nulo.");
        this.grupo = grupo;
    }

    /**
     * Retorna o grupo de clientes associado a este atendimento.
     *
     * @return grupo de clientes
     */
    public GrupoClientes getGrupo() {
        return grupo;
    }
}