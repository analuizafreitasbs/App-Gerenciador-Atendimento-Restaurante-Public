package com.example.model;

import com.example.util.TipoCliente;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um grupo de clientes do restaurante.
 * <p>
 * Implementa {@link Atendivel} para permitir o atendimento de grupos de clientes.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link NullPointerException} - Lançada ao adicionar ou remover clientes ou pedidos nulos.</li>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar um grupo com nome nulo.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar um grupo de clientes com id e nome válidos.</li>
 *   <li>Adicionar clientes e pedidos ao grupo.</li>
 *   <li>Definir hora de chegada e observações gerais.</li>
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
public class GrupoClientes implements Atendivel {
    private int id;
    private String nomeGrupo;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private LocalTime horaChegada;
    private String observacoesGerais;

    /**
     * Construtor do GrupoClientes.
     *
     * @param id        identificador do grupo
     * @param nomeGrupo nome do grupo (não pode ser nulo)
     * @throws IllegalArgumentException se o nome do grupo for nulo
     */
    public GrupoClientes(int id, String nomeGrupo) {
        if (nomeGrupo == null) throw new IllegalArgumentException("Nome do grupo não pode ser nulo.");
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * Adiciona um cliente ao grupo.
     *
     * @param cliente cliente a ser adicionado (não pode ser nulo)
     * @throws NullPointerException se o cliente for nulo
     */
    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) throw new NullPointerException("Cliente não pode ser nulo.");
        clientes.add(cliente);
    }

    /**
     * Adiciona um pedido ao grupo.
     *
     * @param pedido pedido a ser adicionado (não pode ser nulo)
     * @throws NullPointerException se o pedido for nulo
     */
    public void adicionarPedido(Pedido pedido) {
        if (pedido == null) throw new NullPointerException("Pedido não pode ser nulo.");
        pedidos.add(pedido);
    }

    /**
     * Remove um pedido do grupo.
     *
     * @param pedido pedido a ser removido (não pode ser nulo)
     * @throws NullPointerException se o pedido for nulo
     */
    public void removerPedido(Pedido pedido) {
        if (pedido == null) throw new NullPointerException("Pedido não pode ser nulo.");
        pedidos.remove(pedido);
    }

    @Override
    public String getNome() {
        return nomeGrupo;
    }

    @Override
    public TipoCliente getTipoCliente() {
        return clientes.stream().anyMatch(c -> c.getTipoCliente() == TipoCliente.PRIORITARIO) ? TipoCliente.PRIORITARIO : TipoCliente.COMUM;
    }

    @Override
    public List<String> getPreferencias() {
        return clientes.stream().flatMap(c -> c.getPreferencias().stream()).distinct().toList();
    }

    public LocalTime getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(LocalTime horaChegada) {
        this.horaChegada = horaChegada;
    }

    public String getObservacoesGerais() {
        return observacoesGerais;
    }

    public void setObservacoesGerais(String observacoesGerais) {
        this.observacoesGerais = observacoesGerais;
    }
}