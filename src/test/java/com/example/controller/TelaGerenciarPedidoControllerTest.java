package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.AtendimentoIndividual;
import com.example.model.Cliente;
import com.example.model.Garcom;
import com.example.model.ItemPedido;
import com.example.model.ObservacaoDoPedido;
import com.example.model.Pedido;
import com.example.model.Restaurante;
import com.example.util.TipoCliente;
import com.example.util.Turno;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaGerenciarPedidoController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de pedidos,
 * como adicionar itens, adicionar observações, calcular total e verificar estado inicial do pedido.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Adição de item ao pedido e verificação do cálculo do total.</li>
 *   <li>Adição de observação a um item do pedido.</li>
 *   <li>Cálculo do total do pedido com múltiplos itens.</li>
 *   <li>Verificação do estado inicial do pedido (vazio e total zero).</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>O teste utiliza instâncias reais das classes de modelo para simular o fluxo de uso do pedido.</li>
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
class TelaGerenciarPedidoControllerTest {

    private Restaurante restaurante;
    private Garcom garcom;
    private Pedido pedido;
    private AtendimentoIndividual atendimento;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "Garçom Teste", Turno.MANHA);
        pedido = new Pedido();
        cliente = new Cliente(1, "Cliente Teste", TipoCliente.COMUM);
        atendimento = new AtendimentoIndividual(cliente, pedido);
        restaurante.adicionarGarcom(garcom);
        restaurante.adicionarAoCardapio(new ItemPedido("Pizza", 1, 30.0));
    }

    /**
     * Testa se adicionar um item ao pedido funciona corretamente e o total é calculado.
     */
    @Test
    void adicionarItemAoPedidoAdicionaCorretamente() {
        ItemPedido item = restaurante.getCardapio().get(0);
        int quantidade = 2;
        ItemPedido itemParaPedido = new ItemPedido(item.getNome(), quantidade, item.getPreco());
        pedido.adicionarItem(itemParaPedido);
        // Verifica se o item foi adicionado corretamente
        assertTrue(pedido.getItens().contains(itemParaPedido));
        assertEquals(1, pedido.getItens().size());
        assertEquals(60.0, pedido.calcularTotal(), 0.01);
    }

    /**
     * Testa se adicionar uma observação a um item do pedido funciona corretamente.
     */
    @Test
    void adicionarObservacaoAoItemFunciona() {
        ItemPedido item = new ItemPedido("Suco", 1, 8.0);
        pedido.adicionarItem(item);
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem açúcar");
        item.adicionarObservacao(obs);
        assertTrue(item.getObservacoes().contains(obs));
        assertEquals("Sem açúcar", item.getObservacoes().get(0).getDescricao());
    }

    /**
     * Testa o cálculo do total do pedido com múltiplos itens.
     */
    @Test
    void calcularTotalPedidoCorreto() {
        pedido.adicionarItem(new ItemPedido("Pizza", 2, 30.0));
        pedido.adicionarItem(new ItemPedido("Suco", 1, 8.0));
        assertEquals(68.0, pedido.calcularTotal(), 0.01);
    }

    /**
     * Testa se o pedido está inicialmente vazio e o total é zero.
     */
    @Test
    void pedidoInicialmenteVazio() {
        assertTrue(pedido.getItens().isEmpty());
        assertEquals(0.0, pedido.calcularTotal(), 0.01);
    }
}