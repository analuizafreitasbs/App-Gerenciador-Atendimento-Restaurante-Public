package com.example.model;

import com.example.util.Status;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Classe abstrata que representa um atendimento realizado no restaurante.
 * <p>
 * Responsável por controlar o ciclo de vida do atendimento, incluindo o pedido associado,
 * tempos de espera e atendimento, status e horários de início e fim.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada no construtor caso o pedido seja nulo.</li>
 *   <li>{@link NullPointerException} - Pode ser lançada em métodos que dependem de {@code inicio} ou {@code fim} não inicializados.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar uma subclasse de Atendimento passando um {@link Pedido} válido.</li>
 *   <li>Chamar {@link #iniciarAtendimento(LocalTime)} para registrar o início do atendimento.</li>
 *   <li>Chamar {@link #finalizarAtendimento()} para registrar o fim do atendimento.</li>
 *   <li>Consultar tempos e status conforme necessário.</li>
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
public abstract class Atendimento {
    private Pedido pedido;
    private Duration tempoDeEspera;
    private Duration tempoDeAtendimento;
    private Status status;
    private LocalTime inicio;
    private LocalTime fim;

    /**
     * Construtor do Atendimento.
     *
     * @param pedido o pedido associado ao atendimento (não pode ser nulo)
     * @throws IllegalArgumentException se o pedido for nulo
     */
    public Atendimento(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("Pedido não pode ser nulo.");
        this.pedido = pedido;
        this.status = Status.AGUARDANDO;
    }

    /**
     * Inicia o atendimento, registrando o horário de início e calculando o tempo de espera.
     *
     * @param horaChegada horário de chegada do cliente ou grupo
     * @throws NullPointerException se {@code horaChegada} for nulo
     */
    public void iniciarAtendimento(LocalTime horaChegada) {
        if (horaChegada == null) throw new NullPointerException("Hora de chegada não pode ser nula.");
        this.inicio = LocalTime.now();
        this.tempoDeEspera = Duration.between(horaChegada, inicio);
        this.status = Status.EM_ATENDIMENTO;
    }

    /**
     * Finaliza o atendimento, registrando o horário de fim e calculando o tempo de atendimento.
     *
     * @return o próprio atendimento finalizado
     * @throws NullPointerException se {@code inicio} não tiver sido inicializado
     */
    public Atendimento finalizarAtendimento() {
        if (inicio == null) throw new NullPointerException("O atendimento não foi iniciado.");
        this.fim = LocalTime.now();
        this.tempoDeAtendimento = Duration.between(inicio, fim);
        this.status = Status.FINALIZADO;
        return this;
    }

    /**
     * Calcula o tempo total entre o início e o fim do atendimento.
     *
     * @return duração total do atendimento
     * @throws NullPointerException se {@code inicio} ou {@code fim} não tiverem sido inicializados
     */
    public Duration calcularTempoTotal() {
        if (inicio == null || fim == null) throw new NullPointerException("Início ou fim do atendimento não definidos.");
        return Duration.between(inicio, fim);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Duration getTempoDeEspera() {
        return tempoDeEspera;
    }

    public Duration getTempoDeAtendimento() {
        return tempoDeAtendimento;
    }

    public Status getStatus() {
        return status;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }
}