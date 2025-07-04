package com.example.model;

import com.example.util.Status;

import java.util.*;

/**
 * Classe que representa uma fila de atendimentos no restaurante.
 * <p>
 * Utiliza uma {@link PriorityQueue} para gerenciar a ordem dos atendimentos com base no status.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link NullPointerException} - Lançada ao tentar adicionar ou remover um atendimento nulo.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar a fila de atendimento.</li>
 *   <li>Adicionar, remover ou consultar atendimentos conforme necessário.</li>
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
public class FilaDeAtendimento<T extends Atendimento> {
    private PriorityQueue<Atendimento> fila;

    /**
     * Construtor da fila de atendimento.
     * Inicializa a fila com ordenação baseada no status do atendimento.
     */
    public FilaDeAtendimento() {
        this.fila = new PriorityQueue<>(Comparator.comparing(a -> a.getStatus().ordinal()));
    }

    /**
     * Adiciona um atendimento à fila.
     *
     * @param atendimento atendimento a ser adicionado (não pode ser nulo)
     * @throws NullPointerException se o atendimento for nulo
     */
    public void adicionarAtendimento(Atendimento atendimento) {
        if (atendimento == null) throw new NullPointerException("Atendimento não pode ser nulo.");
        fila.add(atendimento);
    }

    /**
     * Remove e retorna o atendimento com maior prioridade da fila.
     *
     * @return o atendimento removido ou {@code null} se a fila estiver vazia
     */
    public Atendimento removerAtendimento() {
        return fila.poll();
    }

    /**
     * Remove um atendimento específico da fila e reordena a fila.
     *
     * @param atendimento atendimento a ser removido (não pode ser nulo)
     * @throws NullPointerException se o atendimento for nulo
     */
    public void removerAtendimentoEspecifico(Atendimento atendimento) {
        if (atendimento == null) throw new NullPointerException("Atendimento não pode ser nulo.");
        fila.remove(atendimento);
        reordenarFila();
    }

    /**
     * Conta o número de atendimentos ativos (não finalizados) na fila.
     *
     * @return quantidade de atendimentos ativos
     */
    public int contarAtendimentosAtivos() {
        return (int) fila.stream()
                .filter(a -> a.getStatus() != Status.FINALIZADO)
                .count();
    }

    /**
     * Remove todos os atendimentos da fila.
     */
    public void limparFila() {
        fila.clear();
    }

    /**
     * Retorna o tamanho atual da fila.
     *
     * @return quantidade de atendimentos na fila
     */
    public int tamanho() {
        return fila.size();
    }

    /**
     * Reordena a fila de acordo com o comparador de status.
     */
    public void reordenarFila() {
        List<Atendimento> lista = new ArrayList<>(fila);
        fila.clear();
        fila.addAll(lista);
    }

    /**
     * Retorna a fila interna de atendimentos.
     *
     * @return fila de atendimentos
     */
    public PriorityQueue<Atendimento> getFila() {
        return fila;
    }
}