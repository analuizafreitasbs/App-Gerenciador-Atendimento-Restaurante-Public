package com.example.model;

import com.example.util.TipoCliente;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um cliente individual do restaurante.
 * <p>
 * Implementa {@link Atendivel} para permitir o atendimento tanto individualmente quanto em grupo.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link NullPointerException} - Lançada ao adicionar ou remover preferências nulas.</li>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar um cliente com nome ou tipo nulos.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar um cliente com id, nome e tipo válidos.</li>
 *   <li>Adicionar ou remover preferências conforme necessário.</li>
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
public class Cliente implements Atendivel {
    private int id;
    private String nome;
    private TipoCliente tipo;
    private List<String> preferencias;
    private LocalTime horaChegada;
    private String observacoesGerais;

    /**
     * Construtor do Cliente.
     *
     * @param id   identificador do cliente
     * @param nome nome do cliente (não pode ser nulo)
     * @param tipo tipo do cliente (não pode ser nulo)
     * @throws IllegalArgumentException se nome ou tipo forem nulos
     */
    public Cliente(int id, String nome, TipoCliente tipo) {
        if (nome == null) throw new IllegalArgumentException("Nome do cliente não pode ser nulo.");
        if (tipo == null) throw new IllegalArgumentException("Tipo do cliente não pode ser nulo.");
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preferencias = new ArrayList<>();
        this.observacoesGerais = "";
    }

    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public TipoCliente getTipoCliente() {
        return tipo;
    }

    @Override
    public List<String> getPreferencias() {
        return preferencias;
    }

    /**
     * Adiciona uma preferência à lista do cliente.
     *
     * @param item preferência a ser adicionada (não pode ser nula)
     * @throws NullPointerException se o item for nulo
     */
    public void adicionarPreferencia(String item) {
        if (item == null) throw new NullPointerException("Preferência não pode ser nula.");
        preferencias.add(item);
    }

    /**
     * Remove uma preferência da lista do cliente.
     *
     * @param item preferência a ser removida (não pode ser nula)
     * @throws NullPointerException se o item for nulo
     */
    public void removerPreferencia(String item) {
        if (item == null) throw new NullPointerException("Preferência não pode ser nula.");
        preferencias.remove(item);
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