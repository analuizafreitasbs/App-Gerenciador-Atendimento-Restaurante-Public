package com.example.model;

import com.example.util.Turno;
import com.example.util.persistence.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe que representa o restaurante e centraliza a gestão de garçons, cardápio, atendimentos e fila de espera.
 * <p>
 * Responsável por distribuir atendimentos, gerenciar turnos, registrar históricos e controlar entidades principais.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException} - Lançada ao criar um restaurante com nome nulo ou vazio, ao adicionar garçom ou item nulo, ao iniciar turno nulo ou ao distribuir tipo de atendível desconhecido.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Instanciar o restaurante com um nome válido.</li>
 *   <li>Adicionar garçons e itens ao cardápio.</li>
 *   <li>Distribuir atendimentos e registrar finalizações.</li>
 *   <li>Gerenciar turnos e consultar pedidos.</li>
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
public class Restaurante {
    private final String nome;
    private final List<Garcom> garcons;
    private final List<ItemPedido> cardapio;
    private final List<Atendimento> historicoAtendimentos;
    private Turno turnoAtual;

    private int nextGarcomId = 1;
    private int nextClienteId = 1;
    private int nextGrupoId = 1;

    private final List<Atendivel> filaDeEsperaGeral;

    /**
     * Construtor do Restaurante.
     *
     * @param nome nome do restaurante (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public Restaurante(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.historicoAtendimentos = new ArrayList<>();
        this.filaDeEsperaGeral = new ArrayList<>();
    }

    /**
     * Inicia o turno do restaurante e de todos os garçons.
     *
     * @param turno turno a ser iniciado (não pode ser nulo)
     * @throws IllegalArgumentException se o turno for nulo
     */
    public void iniciarTurno(Turno turno) {
        if (turno == null) throw new IllegalArgumentException("Turno não pode ser nulo.");
        this.turnoAtual = turno;
        for (Garcom g : garcons) {
            g.setTurnoAtual(turno);
        }
    }

    /**
     * Encerra o turno do restaurante e de todos os garçons, salvando os dados dos garçons.
     */
    public void encerrarTurno() {
        for (Garcom g : garcons) {
            g.setTurnoAtual(null);
        }
        this.turnoAtual = null;
        Persistencia.salvarGarcons(garcons);
    }

    /**
     * Adiciona um garçom ao restaurante.
     *
     * @param garcom garçom a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException se o garçom for nulo
     */
    public void adicionarGarcom(Garcom garcom) {
        if (garcom == null) throw new IllegalArgumentException("Garçom não pode ser nulo.");
        garcons.add(garcom);
    }

    /**
     * Busca um garçom pelo seu identificador.
     *
     * @param id identificador do garçom
     * @return garçom encontrado ou {@code null} se não existir
     */
    public Garcom buscarGarcomPorId(int id) {
        return garcons.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }

    /**
     * Distribui um atendimento para um garçom disponível.
     *
     * @param atendivel entidade a ser atendida (Cliente ou GrupoClientes)
     * @throws IllegalArgumentException se o tipo de atendível for desconhecido
     */
    public void distribuirAtendimento(Atendivel atendivel) {
        if (atendivel instanceof Cliente cliente) {
            for (Garcom g : garcons) {
                if (g.podeAtenderMaisClientesIndividuais()) {
                    g.atenderCliente(cliente);
                    return;
                }
            }
        } else if (atendivel instanceof GrupoClientes grupo) {
            for (Garcom g : garcons) {
                if (g.podeAtenderMaisGrupos()) {
                    g.atenderGrupo(grupo);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Tipo de atendível desconhecido.");
        }
        System.out.println("Nenhum garçom disponível no momento para atender " + atendivel.getNome());
    }

    /**
     * Registra um atendimento finalizado no histórico e remove das filas dos garçons.
     *
     * @param atendimento atendimento finalizado (pode ser nulo, nesse caso nada acontece)
     */
    public void registrarAtendimentoFinalizado(Atendimento atendimento) {
        if (atendimento != null) {
            historicoAtendimentos.add(atendimento);
        }
        for (Garcom g : garcons) {
            if (atendimento instanceof AtendimentoIndividual && g.getFilaAtendimentoIndividual().getFila().contains(atendimento)) {
                g.removerAtendimentoFinalizado(atendimento);
                System.out.println("Atendimento individual de " + ((AtendimentoIndividual) atendimento).getCliente().getNome() + " finalizado e removido da fila do garçom " + g.getNome());
                return;
            } else if (atendimento instanceof AtendimentoGrupo && g.getFilaAtendimentoGrupo().getFila().contains(atendimento)) {
                g.removerAtendimentoFinalizado(atendimento);
                System.out.println("Atendimento de grupo " + ((AtendimentoGrupo) atendimento).getGrupo().getNomeGrupo() + " finalizado e removido da fila do garçom " + g.getNome());
                return;
            }
        }
    }

    /**
     * Busca um pedido pelo seu identificador, procurando primeiro no histórico e depois nas filas ativas dos garçons.
     *
     * @param pedidoId identificador do pedido
     * @return pedido encontrado ou {@code null} se não existir
     */
    public Pedido buscarPedidoPorId(int pedidoId) {
        // 1. Buscar nos atendimentos finalizados
        Optional<Atendimento> atendimentoHistorico = historicoAtendimentos.stream()
                .filter(a -> a.getPedido().getId() == pedidoId)
                .findFirst();
        if (atendimentoHistorico.isPresent()) {
            return atendimentoHistorico.get().getPedido();
        }

        for (Garcom garcom : garcons) {
            Optional<Atendimento> atendimentoIndividualAtivo = garcom.getFilaAtendimentoIndividual().getFila().stream()
                    .filter(a -> a.getPedido().getId() == pedidoId)
                    .findFirst();
            if (atendimentoIndividualAtivo.isPresent()) {
                return atendimentoIndividualAtivo.get().getPedido();
            }

            Optional<Atendimento> atendimentoGrupoAtivo = garcom.getFilaAtendimentoGrupo().getFila().stream()
                    .filter(a -> a.getPedido().getId() == pedidoId)
                    .findFirst();
            if (atendimentoGrupoAtivo.isPresent()) {
                return atendimentoGrupoAtivo.get().getPedido();
            }
        }
        return null; // Pedido não encontrado
    }

    /**
     * Valida o login de um garçom pelo id e nome.
     *
     * @param id   identificador do garçom
     * @param nome nome do garçom
     * @return garçom correspondente ou {@code null} se não encontrado
     */
    public Garcom validarLoginGarcom(int id, String nome) {
        Optional<Garcom> garcom = garcons.stream()
                .filter(g -> g.getId() == id && g.getNome().equalsIgnoreCase(nome))
                .findFirst();
        return garcom.orElse(null);
    }

    /**
     * Adiciona um item ao cardápio do restaurante.
     *
     * @param item item a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException se o item for nulo
     */
    public void adicionarAoCardapio(ItemPedido item) {
        if (item == null) throw new IllegalArgumentException("Item do cardápio não pode ser nulo.");
        cardapio.add(item);
    }

    /**
     * Gera um novo identificador para garçom.
     *
     * @return novo id de garçom
     */
    public int gerarNovoGarcomId() {
        return nextGarcomId++;
    }

    /**
     * Gera um novo identificador para cliente.
     *
     * @return novo id de cliente
     */
    public int gerarNovoClienteId() {
        return nextClienteId++;
    }

    /**
     * Gera um novo identificador para grupo de clientes.
     *
     * @return novo id de grupo
     */
    public int gerarNovoGrupoId() {
        return nextGrupoId++;
    }

    // Getters

    /**
     * Retorna o nome do restaurante.
     *
     * @return nome do restaurante
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a lista de garçons do restaurante.
     *
     * @return lista de garçons
     */
    public List<Garcom> getGarcons() {
        return new ArrayList<>(garcons);
    }

    /**
     * Retorna o cardápio do restaurante.
     *
     * @return lista de itens do cardápio
     */
    public List<ItemPedido> getCardapio() {
        return new ArrayList<>(cardapio);
    }

    /**
     * Retorna o histórico de atendimentos finalizados.
     *
     * @return lista de atendimentos finalizados
     */
    public List<Atendimento> getHistoricoAtendimentos() {
        return new ArrayList<>(historicoAtendimentos);
    }

    /**
     * Retorna o turno atual do restaurante.
     *
     * @return turno atual
     */
    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    /**
     * Retorna a fila de espera geral do restaurante.
     *
     * @return lista de entidades aguardando atendimento
     */
    public List<Atendivel> getFilaDeEsperaGeral() {
        return filaDeEsperaGeral;
    }
}