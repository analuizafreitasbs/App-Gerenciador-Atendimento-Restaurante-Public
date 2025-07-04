package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.*;
import com.example.util.Status;
import com.example.util.TipoCliente;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaAtendimentoController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de atendimentos,
 * filas, cadastro de clientes/grupos e finalização de atendimentos.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o garçom pode atender mais grupos conforme o limite definido.</li>
 *   <li>Testa o cadastro de novos clientes na fila de espera geral do restaurante.</li>
 *   <li>Testa o cadastro de novos grupos e o correto atendimento pelo garçom.</li>
 *   <li>Testa a finalização de um atendimento de grupo, verificando a mudança de status.</li>
 *   <li>Testa a reordenação da fila de espera geral para priorizar clientes prioritários.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Mocks simples de Restaurante e Garcom são utilizados para isolar o teste.</li>
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
public class TelaAtendimentoControllerTest {

    private Restaurante restauranteMock;
    private Garcom garcomMock;

    @BeforeEach
    public void setUp() {
        restauranteMock = new Restaurante("Restaurante Teste");
        garcomMock = new Garcom(1, "João", null);

        restauranteMock.adicionarGarcom(garcomMock);
    }

    /**
     * Testa se o garçom pode atender mais grupos até atingir o limite.
     */
    @Test
    public void testGarcomPodeAtenderMaisGrupos() {
        assertTrue(garcomMock.podeAtenderMaisGrupos());

        // Simular o garçom atendendo 3 grupos para atingir o limite
        GrupoClientes grupo1 = new GrupoClientes(1, "Grupo1");
        grupo1.setHoraChegada(java.time.LocalTime.now());
        GrupoClientes grupo2 = new GrupoClientes(2, "Grupo2");
        grupo2.setHoraChegada(java.time.LocalTime.now());
        GrupoClientes grupo3 = new GrupoClientes(3, "Grupo3");
        grupo3.setHoraChegada(java.time.LocalTime.now());

        garcomMock.atenderGrupo(grupo1);
        garcomMock.atenderGrupo(grupo2);
        garcomMock.atenderGrupo(grupo3);

        assertFalse(garcomMock.podeAtenderMaisGrupos());
    }

    /**
     * Testa o cadastro de um novo cliente na fila de espera geral do restaurante.
     */
    @Test
    public void testCadastrarNovoCliente() {
        Cliente cliente = new Cliente(1, "Maria", TipoCliente.PRIORITARIO);
        // Simula fila de espera geral
        restauranteMock.getFilaDeEsperaGeral().add(cliente);

        assertTrue(restauranteMock.getFilaDeEsperaGeral().contains(cliente));
        assertEquals("Maria", restauranteMock.getFilaDeEsperaGeral().get(0).getNome());
    }

    /**
     * Testa o cadastro de um novo grupo e o correto atendimento pelo garçom.
     */
    @Test
    public void testCadastrarNovoGrupo() {
        GrupoClientes grupo = new GrupoClientes(1, "Família Silva");
        grupo.setHoraChegada(java.time.LocalTime.now());
        garcomMock.atenderGrupo(grupo);

        assertEquals(1, garcomMock.getFilaAtendimentoGrupo().tamanho());
        AtendimentoGrupo atendimento = (AtendimentoGrupo) garcomMock.getFilaAtendimentoGrupo().getFila().peek();
        assertEquals("Família Silva", atendimento.getGrupo().getNomeGrupo());
    }

    /**
     * Testa a finalização de um atendimento de grupo, verificando a mudança de status.
     */
    @Test
    public void testFinalizarAtendimento() {
        GrupoClientes grupo = new GrupoClientes(1, "Grupo Teste");
        grupo.setHoraChegada(java.time.LocalTime.now());
        garcomMock.atenderGrupo(grupo);
        AtendimentoGrupo atendimento = (AtendimentoGrupo) garcomMock.getFilaAtendimentoGrupo().getFila().peek();

        assertEquals(Status.EM_ATENDIMENTO, atendimento.getStatus());

        atendimento.finalizarAtendimento();

        assertEquals(Status.FINALIZADO, atendimento.getStatus());
    }

    /**
     * Testa a reordenação da fila de espera geral para priorizar clientes prioritários.
     */
    @Test
    public void testReordenarFilaPorPrioridade() {
        Cliente normal = new Cliente(1, "Cliente Normal", TipoCliente.COMUM);
        Cliente prioritario = new Cliente(2, "Cliente Prioritário", TipoCliente.PRIORITARIO);

        restauranteMock.getFilaDeEsperaGeral().add(normal);
        restauranteMock.getFilaDeEsperaGeral().add(prioritario);

        // Reordenar: prioritários primeiro
        restauranteMock.getFilaDeEsperaGeral().sort((a, b) -> (a.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1)
                - (b.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1));

        assertEquals(TipoCliente.PRIORITARIO, restauranteMock.getFilaDeEsperaGeral().get(0).getTipoCliente());
    }
}