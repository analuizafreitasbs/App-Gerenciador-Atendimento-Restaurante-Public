package com.example.controller;

import java.io.IOException;

import com.example.model.Atendimento;
import com.example.model.AtendimentoGrupo;
import com.example.model.AtendimentoIndividual;
import com.example.model.Cliente;
import com.example.model.Garcom;
import com.example.model.GrupoClientes;
import com.example.model.ItemPedido;
import com.example.model.ObservacaoDoPedido;
import com.example.model.Pedido;
import com.example.model.Restaurante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador da tela de gerenciamento de pedidos.
 * <p>
 * Permite ao garçom adicionar itens ao pedido, adicionar observações, visualizar e atualizar o total do pedido.
 * Trata exceções de IO, validação e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Exibir e filtrar itens do cardápio.</li>
 *   <li>Adicionar itens ao pedido e observações aos itens.</li>
 *   <li>Exibir e atualizar o total do pedido.</li>
 *   <li>Confirmar e salvar o pedido.</li>
 *   <li>Navegar de volta para a tela de atendimento do garçom.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Garcom, Atendimento, Pedido, ItemPedido, ObservacaoDoPedido, Cliente, GrupoClientes.</li>
 *   <li>JavaFX: ListView, TextField, Spinner, TextArea, Label, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaGerenciarPedidoController {

    /** Referência ao restaurante em uso */
    private Restaurante restaurante;
    /** Garçom atualmente logado */
    private Garcom garcomLogado;
    /** Atendimento atual */
    private Atendimento atendimentoAtual;
    /** Pedido atual */
    private Pedido pedidoAtual;

    @FXML
    private Label labelTituloAtendimento;
    @FXML
    private ListView<String> listViewCardapio;
    @FXML
    private TextField filtroCardapio;
    @FXML
    private ListView<String> listViewItensPedido;
    @FXML
    private Label labelTotalPedido;
    @FXML
    private Spinner<Integer> spinnerQuantidade;
    @FXML
    private TextArea textAreaObservacoesItem;
    @FXML
    private TextArea textAreaObservacoesCliente;
    @FXML
    private Label labelPedidoId;
    @FXML
    private Label labelStatusPedido;

    /** Lista observável dos itens do cardápio */
    private ObservableList<ItemPedido> itensCardapioObservableList;
    /** Lista observável dos itens do pedido */
    private ObservableList<ItemPedido> itensPedidoObservableList;

    /**
     * Define o restaurante utilizado pelo controlador e carrega o cardápio.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        itensCardapioObservableList = FXCollections.observableArrayList(restaurante.getCardapio());
        carregarCardapio();
    }

    /**
     * Define o garçom logado.
     * @param garcom Garçom logado
     */
    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
    }

    /**
     * Define o atendimento atual e inicializa os campos da tela.
     * @param atendimento Atendimento atual
     */
    public void setAtendimentoAtual(Atendimento atendimento) {
        this.atendimentoAtual = atendimento;
        this.pedidoAtual = atendimento.getPedido();
        if (atendimentoAtual instanceof AtendimentoIndividual) {
            Cliente cliente = ((AtendimentoIndividual) atendimentoAtual).getCliente();
            labelTituloAtendimento.setText("Pedido para: " + cliente.getNome());
            textAreaObservacoesCliente.setText(cliente.getObservacoesGerais());
        } else if (atendimentoAtual instanceof AtendimentoGrupo) {
            GrupoClientes grupo = ((AtendimentoGrupo) atendimentoAtual).getGrupo();
            labelTituloAtendimento.setText("Pedido para: " + grupo.getNomeGrupo());
            textAreaObservacoesCliente.setText(grupo.getObservacoesGerais());
        }

        labelPedidoId.setText("ID do Pedido: " + pedidoAtual.getId());
        labelStatusPedido.setText("Status do Pedido: " + atendimentoAtual.getStatus().toString());

        // Carrega os itens do pedido existentes
        itensPedidoObservableList = FXCollections.observableArrayList(pedidoAtual.getItens());
        listViewItensPedido.setItems(formatarItensPedido(itensPedidoObservableList));
        atualizarTotalPedido();
    }

    /**
     * Inicializa componentes da tela e listeners.
     */
    @FXML
    public void initialize() {
        spinnerQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
        filtroCardapio.textProperty().addListener((obs, oldVal, newVal) -> filtrarCardapio(newVal));
        textAreaObservacoesCliente.setEditable(false);
    }

    /**
     * Carrega os itens do cardápio para o ListView.
     */
    private void carregarCardapio() {
        listViewCardapio.getItems().clear();
        if (itensCardapioObservableList != null) {
            itensCardapioObservableList.forEach(item ->
                    listViewCardapio.getItems().add(item.getNome() + " - R$" + String.format("%.2f", item.getPreco())));
        }
    }

    /**
     * Filtra os itens do cardápio conforme o texto digitado no campo de filtro.
     * @param filtro Texto para filtrar os itens
     */
    private void filtrarCardapio(String filtro) {
        listViewCardapio.getItems().clear();
        if (itensCardapioObservableList != null) {
            itensCardapioObservableList.stream()
                    .filter(item -> item.getNome().toLowerCase().contains(filtro.toLowerCase()))
                    .forEach(item -> listViewCardapio.getItems().add(item.getNome() + " - R$" + String.format("%.2f", item.getPreco())));
        }
    }

    /**
     * Formata a lista de itens do pedido para exibição.
     * @param itens Lista de itens do pedido
     * @return Lista observável de strings formatadas
     */
    private ObservableList<String> formatarItensPedido(ObservableList<ItemPedido> itens) {
        ObservableList<String> formattedList = FXCollections.observableArrayList();
        itens.forEach(item -> {
            StringBuilder itemString = new StringBuilder();
            itemString.append(item.getNome())
                    .append(" (x").append(item.getQuantidade())
                    .append(") - R$ ").append(String.format("%.2f", item.calcularSubtotal()));
            if (!item.getObservacoes().isEmpty()) {
                itemString.append(" [Obs: ");
                item.getObservacoes().forEach(obs -> itemString.append(obs.getDescricao()).append("; "));
                itemString.append("]");
            }
            formattedList.add(itemString.toString());
        });
        return formattedList;
    }

    /**
     * Atualiza a exibição da lista de itens do pedido.
     */
    private void atualizarListaItensPedido() {
        listViewItensPedido.setItems(formatarItensPedido(itensPedidoObservableList));
    }

    /**
     * Atualiza o valor total do pedido.
     */
    private void atualizarTotalPedido() {
        double total = pedidoAtual.calcularTotal();
        labelTotalPedido.setText("TOTAL: R$ " + String.format("%.2f", total));
    }

    /**
     * Adiciona um item selecionado do cardápio ao pedido.
     * Trata exceções de seleção e exibe mensagens apropriadas.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        try {
            String selectedItemString = listViewCardapio.getSelectionModel().getSelectedItem();
            if (selectedItemString == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um item do cardápio para adicionar.").showAndWait();
                return;
            }

            String nomeItem = selectedItemString.substring(0, selectedItemString.indexOf(" - R$"));
            ItemPedido itemSelecionado = restaurante.getCardapio().stream()
                    .filter(item -> item.getNome().equals(nomeItem))
                    .findFirst().orElse(null);

            if (itemSelecionado != null) {
                int quantidade = spinnerQuantidade.getValue();
                ItemPedido itemParaPedido = new ItemPedido(itemSelecionado.getNome(), quantidade, itemSelecionado.getPreco());
                pedidoAtual.adicionarItem(itemParaPedido);
                itensPedidoObservableList.add(itemParaPedido);
                atualizarListaItensPedido();
                atualizarTotalPedido();
                new Alert(Alert.AlertType.INFORMATION, itemParaPedido.getNome() + " (x" + quantidade + ") adicionado ao pedido.").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao adicionar item ao pedido: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Adiciona uma observação ao item selecionado do pedido.
     * Trata exceções de seleção e exibe mensagens apropriadas.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAdicionarObservacaoItem(ActionEvent event) {
        try {
            String selectedItemPedidoString = listViewItensPedido.getSelectionModel().getSelectedItem();
            if (selectedItemPedidoString == null) {
                new Alert(Alert.AlertType.WARNING, "Selecione um item no pedido para adicionar observação.").showAndWait();
                return;
            }

            ItemPedido itemNoPedido = null;
            for (ItemPedido item : itensPedidoObservableList) {
                String itemDisplayStringStart = item.getNome() + " (x" + item.getQuantidade() + ")";
                if (selectedItemPedidoString.startsWith(itemDisplayStringStart)) {
                    itemNoPedido = item;
                    break;
                }
            }

            if (itemNoPedido != null) {
                String observacaoTexto = textAreaObservacoesItem.getText().trim();
                if (!observacaoTexto.isEmpty()) {
                    itemNoPedido.adicionarObservacao(new ObservacaoDoPedido(observacaoTexto));
                    atualizarListaItensPedido();
                    textAreaObservacoesItem.clear();
                    new Alert(Alert.AlertType.INFORMATION, "Observação adicionada a " + itemNoPedido.getNome() + ".").showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Digite a observação a ser adicionada.").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Erro ao encontrar o item selecionado no pedido. Tente novamente.").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao adicionar observação: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Confirma e salva o pedido, exibindo mensagem de sucesso.
     * Retorna para a tela de atendimento.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleConfirmarPedido(ActionEvent event) {
        try {
            new Alert(Alert.AlertType.INFORMATION, "Pedido atualizado e salvo!").showAndWait();
            handleVoltar(event);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao confirmar pedido: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Volta para a tela de atendimento do garçom.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaAtendimento.fxml"));
            Parent root = loader.load();

            TelaAtendimentoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            controller.setGarcomLogado(garcomLogado);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Atendimento - Garçom: " + garcomLogado.getNome());
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para a tela de atendimentos: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao voltar: " + e.getMessage()).showAndWait();
        }
    }
}