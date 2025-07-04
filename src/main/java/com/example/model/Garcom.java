package com.example.model;

import com.example.util.Turno;

/**
 * Classe que representa um garçom do restaurante.
 * <p>
 * Responsável por gerenciar os atendimentos individuais e em grupo, além de controlar o turno e limites de atendimento.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar um garçom com nome nulo ou vazio.</li>
 *   <li>{@link NullPointerException} - Pode ser lançada ao tentar acessar métodos de objetos nulos (ex: cliente ou grupo nulo).</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar um garçom com id, nome e turno.</li>
 *   <li>Chamar métodos para atender clientes individuais ou grupos, respeitando os limites.</li>
 *   <li>Remover atendimentos finalizados e reordenar filas conforme necessário.</li>
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
public class Garcom {
    private final int id;
    private final String nome;
    private Turno turnoAtual;

    private final FilaDeAtendimento<AtendimentoIndividual> filaAtendimentoIndividual;
    private final FilaDeAtendimento<AtendimentoGrupo> filaAtendimentoGrupo;

    /**
     * Construtor do Garcom.
     *
     * @param id         identificador do garçom
     * @param nome       nome do garçom (não pode ser nulo ou vazio)
     * @param turnoAtual turno atual do garçom (pode ser nulo)
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public Garcom(int id, String nome, Turno turnoAtual) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do garçom não pode ser vazio.");
        this.id = id;
        this.nome = nome;
        this.turnoAtual = turnoAtual;
        this.filaAtendimentoIndividual = new FilaDeAtendimento<>();
        this.filaAtendimentoGrupo = new FilaDeAtendimento<>();
    }

    /**
     * Verifica se o garçom pode atender mais clientes individuais (máximo de 5).
     *
     * @return true se pode atender mais, false caso contrário
     */
    public boolean podeAtenderMaisClientesIndividuais() {
        return filaAtendimentoIndividual.tamanho() < 5;
    }

    /**
     * Verifica se o garçom pode atender mais grupos (máximo de 3).
     *
     * @return true se pode atender mais, false caso contrário
     */
    public boolean podeAtenderMaisGrupos() {
        return filaAtendimentoGrupo.tamanho() < 3;
    }

    /**
     * Inicia o atendimento de um cliente individual.
     *
     * @param cliente cliente a ser atendido (não pode ser nulo)
     * @throws NullPointerException se o cliente for nulo
     */
    public void atenderCliente(Cliente cliente) {
        if (cliente == null) throw new NullPointerException("Cliente não pode ser nulo.");
        if (!podeAtenderMaisClientesIndividuais()) {
            System.out.println("Limite de atendimentos individuais atingido.");
            return;
        }
        Pedido pedido = new Pedido();
        AtendimentoIndividual atendimento = new AtendimentoIndividual(cliente, pedido);
        atendimento.iniciarAtendimento(cliente.getHoraChegada());
        filaAtendimentoIndividual.adicionarAtendimento(atendimento);
    }

    /**
     * Inicia o atendimento de um grupo de clientes.
     *
     * @param grupo grupo de clientes a ser atendido (não pode ser nulo)
     * @throws NullPointerException se o grupo for nulo
     */
    public void atenderGrupo(GrupoClientes grupo) {
        if (grupo == null) throw new NullPointerException("Grupo de clientes não pode ser nulo.");
        if (!podeAtenderMaisGrupos()) {
            System.out.println("Limite de atendimentos em grupo atingido.");
            return;
        }
        Pedido pedido = new Pedido();
        AtendimentoGrupo atendimento = new AtendimentoGrupo(grupo, pedido);
        atendimento.iniciarAtendimento(grupo.getHoraChegada());
        filaAtendimentoGrupo.adicionarAtendimento(atendimento);
    }

    /**
     * Remove um atendimento finalizado das filas correspondentes.
     *
     * @param atendimento atendimento a ser removido (não pode ser nulo)
     * @throws NullPointerException se o atendimento for nulo
     */
    public void removerAtendimentoFinalizado(Atendimento atendimento) {
        if (atendimento == null) throw new NullPointerException("Atendimento não pode ser nulo.");
        if (atendimento instanceof AtendimentoIndividual) {
            filaAtendimentoIndividual.removerAtendimentoEspecifico(atendimento);
        } else if (atendimento instanceof AtendimentoGrupo) {
            filaAtendimentoGrupo.removerAtendimentoEspecifico(atendimento);
        }
    }

    /**
     * Limpa todas as filas de atendimento do garçom.
     */
    public void limparFilasDeAtendimento() {
        filaAtendimentoIndividual.limparFila();
        filaAtendimentoGrupo.limparFila();
    }

    /**
     * Reordena a fila de atendimentos individuais.
     */
    public void reordenarFilaIndividuais() {
        filaAtendimentoIndividual.reordenarFila();
    }

    /**
     * Reordena a fila de atendimentos em grupo.
     */
    public void reordenarFilaGrupos() {
        filaAtendimentoGrupo.reordenarFila();
    }

    // Getters e Setters

    /**
     * Retorna o identificador do garçom.
     *
     * @return id do garçom
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do garçom.
     *
     * @return nome do garçom
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o turno atual do garçom.
     *
     * @return turno atual
     */
    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    /**
     * Define o turno atual do garçom.
     *
     * @param turnoAtual novo turno
     */
    public void setTurnoAtual(Turno turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    /**
     * Retorna a fila de atendimentos individuais.
     *
     * @return fila de atendimentos individuais
     */
    public FilaDeAtendimento<AtendimentoIndividual> getFilaAtendimentoIndividual() {
        return filaAtendimentoIndividual;
    }

    /**
     * Retorna a fila de atendimentos em grupo.
     *
     * @return fila de atendimentos em grupo
     */
    public FilaDeAtendimento<AtendimentoGrupo> getFilaAtendimentoGrupo() {
        return filaAtendimentoGrupo;
    }
}

