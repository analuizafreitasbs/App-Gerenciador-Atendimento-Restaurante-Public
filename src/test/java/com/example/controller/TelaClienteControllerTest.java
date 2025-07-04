package com.example.controller;

import com.example.model.Cliente;
import com.example.model.GrupoClientes;
import com.example.model.Pedido;
import com.example.model.Restaurante;
import com.example.model.AtendimentoIndividual;
import com.example.util.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaClienteController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao registro de chegada de clientes
 * individuais e grupos, além da busca de pedidos por ID.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Registro de chegada de cliente individual na fila de espera geral do restaurante.</li>
 *   <li>Registro de chegada de grupo de clientes na fila de espera geral do restaurante.</li>
 *   <li>Busca de pedido por ID inexistente (deve retornar null).</li>
 *   <li>Busca de pedido por ID existente (deve retornar o pedido correto).</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>O teste de busca de pedido por ID existente simula o fluxo completo de atendimento e registro.</li>
 * </ul>
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
public class TelaClienteControllerTest {

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
    }

    /**
     * Testa o registro de chegada de um cliente individual na fila de espera geral.
     */
    @Test
    public void testRegistrarChegadaClienteIndividual() {
        String nome = "Maria";
        TipoCliente tipo = TipoCliente.PRIORITARIO;

        Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(), nome, tipo);
        novoCliente.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(novoCliente);

        assertEquals(1, restaurante.getFilaDeEsperaGeral().size());
        assertEquals("Maria", restaurante.getFilaDeEsperaGeral().get(0).getNome());
        assertEquals(TipoCliente.PRIORITARIO, restaurante.getFilaDeEsperaGeral().get(0).getTipoCliente());
    }

    /**
     * Testa o registro de chegada de um grupo de clientes na fila de espera geral.
     */
    @Test
    public void testRegistrarChegadaGrupo() {
        String nomeGrupo = "Família Silva";
        int numPessoas = 3;

        GrupoClientes grupo = new GrupoClientes(restaurante.gerarNovoGrupoId(), nomeGrupo);
        for (int i = 0; i < numPessoas; i++) {
            Cliente membro = new Cliente(restaurante.gerarNovoClienteId(), "Membro " + (i + 1), TipoCliente.COMUM);
            grupo.adicionarCliente(membro);
        }
        grupo.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(grupo);

        assertEquals(1, restaurante.getFilaDeEsperaGeral().size());
        GrupoClientes grupoRegistrado = (GrupoClientes) restaurante.getFilaDeEsperaGeral().get(0);
        assertEquals(nomeGrupo, grupoRegistrado.getNomeGrupo());
        assertEquals(numPessoas, grupoRegistrado.getClientes().size());
    }

    /**
     * Testa a busca de um pedido por ID inexistente (deve retornar null).
     */
    @Test
    public void testBuscarPedidoPorIdNaoEncontrado() {
        int pedidoId = 999;
        Pedido pedido = restaurante.buscarPedidoPorId(pedidoId);
        assertNull(pedido);
    }

    /**
     * Testa a busca de um pedido por ID existente, simulando o fluxo completo de atendimento e registro.
     */
    @Test
    public void testBuscarPedidoPorIdEncontrado() {
        Cliente cliente = new Cliente(restaurante.gerarNovoClienteId(), "João", TipoCliente.COMUM);

        // Defina a hora de chegada ANTES de atender o cliente!
        cliente.setHoraChegada(LocalTime.now());

        // Cria e adiciona um garçom ao restaurante
        com.example.model.Garcom garcom = new com.example.model.Garcom(1, "Garçom Teste", null);
        restaurante.adicionarGarcom(garcom);

        // Faz o garçom atender o cliente (isso cria o atendimento e adiciona à fila corretamente)
        garcom.atenderCliente(cliente);

        // Recupera o atendimento criado pelo método acima
        AtendimentoIndividual atendimentoCriado = (AtendimentoIndividual) garcom.getFilaAtendimentoIndividual()
                .getFila().peek();

        // Finaliza o atendimento antes de registrar
        atendimentoCriado.finalizarAtendimento();

        // Registra o atendimento finalizado corretamente
        restaurante.registrarAtendimentoFinalizado(atendimentoCriado);

        Pedido encontrado = restaurante.buscarPedidoPorId(atendimentoCriado.getPedido().getId());
        assertNotNull(encontrado);
        assertEquals(atendimentoCriado.getPedido().getId(), encontrado.getId());
    }
}