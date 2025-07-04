package com.example.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

import com.example.model.Cliente;
import com.example.model.GrupoClientes;
import com.example.model.ItemPedido;
import com.example.model.Pedido;
import com.example.model.Restaurante;
import com.example.util.TipoCliente;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controlador da tela de interação do cliente.
 * <p>
 * Permite registrar chegada de clientes individuais ou grupos, buscar pedidos pelo ID e visualizar detalhes.
 * Trata exceções de IO, validação e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Registrar chegada de clientes individuais e grupos na fila de espera do restaurante.</li>
 *   <li>Buscar e exibir detalhes de pedidos pelo ID informado.</li>
 *   <li>Exibir status do pedido e detalhes dos itens.</li>
 *   <li>Navegar de volta ao menu principal.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Cliente, GrupoClientes, Pedido, ItemPedido.</li>
 *   <li>JavaFX: TextField, ComboBox, Spinner, Label, TextArea, Dialog, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaClienteController {

    /** Campo de texto para o nome do cliente */
    @FXML
    private TextField nomeField;
    /** ComboBox para seleção do tipo de cliente */
    @FXML
    private ComboBox<TipoCliente> tipoComboBox;
    /** Campo para digitar o ID do pedido */
    @FXML
    private TextField pedidoIdField;
    /** Label para exibir o status do pedido */
    @FXML
    private Label labelStatusPedido;
    /** Área de texto para exibir detalhes do pedido */
    @FXML
    private TextArea textAreaDetalhesPedido;

    /** Referência ao restaurante em uso */
    private Restaurante restaurante;

    /**
     * Define o restaurante utilizado pelo controlador e inicializa o ComboBox.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        tipoComboBox.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        tipoComboBox.getSelectionModel().selectFirst();
        limparDetalhesPedido();
    }

    /**
     * Registra a chegada de um cliente individual na fila de espera.
     * Valida os campos e exibe mensagens apropriadas.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleRegistrarChegada(ActionEvent event) {
        String nome = nomeField.getText().trim();
        TipoCliente tipo = tipoComboBox.getSelectionModel().getSelectedItem();

        if (nome.isEmpty() || tipo == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, preencha seu nome e selecione o tipo de cliente.").showAndWait();
            return;
        }

        try {
            Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(), nome, tipo);
            novoCliente.setHoraChegada(LocalTime.now());
            restaurante.getFilaDeEsperaGeral().add(novoCliente);

            new Alert(Alert.AlertType.INFORMATION, "Olá, " + nome + "! Sua chegada foi registrada. Por favor, aguarde ser chamado(a).").showAndWait();

            nomeField.clear();
            tipoComboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao registrar chegada: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Abre um diálogo para registrar a chegada de um grupo de clientes.
     * Valida os campos e trata exceções.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleRegistrarGrupo(ActionEvent event) {
        Dialog<GrupoClientes> dialog = new Dialog<>();
        dialog.setTitle("Registrar Chegada de Grupo");
        dialog.setHeaderText("Cadastre as informações do seu Grupo");

        ButtonType registrarButtonType = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeGrupoField = new TextField();
        nomeGrupoField.setPromptText("Nome do Grupo (Ex: Família Silva)");
        Spinner<Integer> numClientesSpinner = new Spinner<>(1, 100, 1);

        grid.add(new Label("Nome do Grupo:"), 0, 0);
        grid.add(nomeGrupoField, 1, 0);
        grid.add(new Label("Número de Pessoas:"), 0, 1);
        grid.add(numClientesSpinner, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registrarButtonType) {
                if (!nomeGrupoField.getText().trim().isEmpty() && numClientesSpinner.getValue() > 0) {
                    GrupoClientes novoGrupo = new GrupoClientes(restaurante.gerarNovoGrupoId(), nomeGrupoField.getText().trim());
                    for (int i = 0; i < numClientesSpinner.getValue(); i++) {
                        novoGrupo.adicionarCliente(new Cliente(0, "Membro " + (i + 1), TipoCliente.COMUM));
                    }
                    novoGrupo.setHoraChegada(LocalTime.now());
                    return novoGrupo;
                }
            }
            return null;
        });

        try {
            Optional<GrupoClientes> result = dialog.showAndWait();
            result.ifPresent(grupo -> {
                try {
                    restaurante.getFilaDeEsperaGeral().add(grupo);
                    new Alert(Alert.AlertType.INFORMATION, "A chegada do Grupo '" + grupo.getNomeGrupo() + "' com " + grupo.getClientes().size() + " pessoas foi registrada! Por favor, aguardem ser chamados.").showAndWait();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao registrar grupo: " + e.getMessage()).showAndWait();
                }
            });
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao registrar grupo: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Busca um pedido pelo ID informado e exibe seus detalhes e status.
     * Trata exceções de conversão e busca.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleBuscarPedido(ActionEvent event) {
        limparDetalhesPedido();

        try {
            int pedidoId = Integer.parseInt(pedidoIdField.getText().trim());
            Pedido pedidoEncontrado = restaurante.buscarPedidoPorId(pedidoId);

            if (pedidoEncontrado != null) {
                // Buscar o status do atendimento associado ao pedido
                Optional<String> statusOptional = restaurante.getHistoricoAtendimentos().stream()
                        .filter(at -> at.getPedido().getId() == pedidoEncontrado.getId())
                        .map(at -> at.getStatus().toString())
                        .findFirst();

                String status = statusOptional.orElse("Em Andamento");
                labelStatusPedido.setText("Status: " + status);

                // Montar os detalhes do pedido
                StringBuilder detalhes = new StringBuilder();
                detalhes.append("ID do Pedido: ").append(pedidoEncontrado.getId()).append("\n");
                detalhes.append("Itens:\n");

                for (ItemPedido item : pedidoEncontrado.getItens()) {
                    detalhes.append("- ").append(item.getNome())
                            .append(" (x").append(item.getQuantidade())
                            .append(") - R$ ").append(String.format("%.2f", item.calcularSubtotal()))
                            .append("\n");

                    if (!item.getObservacoes().isEmpty()) {
                        detalhes.append("  Observações: ");
                        item.getObservacoes().forEach(obs -> detalhes.append(obs.getDescricao()).append("; "));
                        detalhes.append("\n");
                    }
                }

                detalhes.append("\nTOTAL: R$ ").append(String.format("%.2f", pedidoEncontrado.calcularTotal()));
                textAreaDetalhesPedido.setText(detalhes.toString());

            } else {
                labelStatusPedido.setText("Status: Pedido não encontrado");
                textAreaDetalhesPedido.setText("Não foi possível encontrar um pedido com o ID " + pedidoId + ".");
                new Alert(Alert.AlertType.INFORMATION, "Pedido não encontrado.").showAndWait();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Por favor, digite um ID de pedido válido (apenas números).").showAndWait();
            limparDetalhesPedido();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao buscar pedido: " + e.getMessage()).showAndWait();
            limparDetalhesPedido();
        }
    }

    /**
     * Limpa os campos de detalhes do pedido.
     */
    private void limparDetalhesPedido() {
        labelStatusPedido.setText("Status: -");
        textAreaDetalhesPedido.clear();
    }

    /**
     * Volta para o menu principal do sistema.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVoltarMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController menuController = loader.getController();
            menuController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao voltar: " + e.getMessage()).showAndWait();
        }
    }
}