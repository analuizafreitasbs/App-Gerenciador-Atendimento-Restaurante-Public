package com.example.controller;

import java.util.Optional;

import com.example.model.Garcom;
import com.example.model.Restaurante;
import com.example.util.Turno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador da tela de gerenciamento de garçons.
 * <p>
 * Permite visualizar, adicionar e alterar turno dos garçons cadastrados no restaurante.
 * Trata exceções e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Exibir a lista de garçons cadastrados.</li>
 *   <li>Adicionar novos garçons ao restaurante.</li>
 *   <li>Alterar o turno de garçons existentes.</li>
 *   <li>Voltar ao menu principal do sistema.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Garcom, Turno.</li>
 *   <li>JavaFX: TableView, TableColumn, TextInputDialog, ChoiceDialog, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaGarconsController {

    /** Tabela que exibe os garçons */
    @FXML
    private TableView<Garcom> tableViewGarcons;
    /** Coluna de ID do garçom */
    @FXML
    private TableColumn<Garcom, Integer> colId;
    /** Coluna de nome do garçom */
    @FXML
    private TableColumn<Garcom, String> colNome;
    /** Coluna de turno do garçom */
    @FXML
    private TableColumn<Garcom, Turno> colTurno;

    /** Lista observável dos garçons */
    private ObservableList<Garcom> garconsObservableList;
    /** Referência ao restaurante em uso */
    private Restaurante restaurante;
    /** Próximo ID disponível para cadastro de garçom */
    private static int nextGarcomId = 1;

    /**
     * Define o restaurante utilizado pelo controlador e carrega os garçons.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (this.restaurante != null) {
            carregarGarcons();
        }
    }

    /**
     * Inicializa as colunas da tabela e a lista observável.
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turnoAtual"));

        garconsObservableList = FXCollections.observableArrayList();
        tableViewGarcons.setItems(garconsObservableList);
    }

    /**
     * Carrega os garçons do restaurante para a tabela.
     */
    private void carregarGarcons() {
        if (restaurante != null) {
            garconsObservableList.clear();
            garconsObservableList.addAll(restaurante.getGarcons());
            nextGarcomId = restaurante.getGarcons().stream().mapToInt(Garcom::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Adiciona um novo garçom ao restaurante após validação do nome.
     * Exibe mensagens de erro em caso de falha.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAdicionarGarcom(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Garçom");
        dialog.setHeaderText("Novo Garçom");
        dialog.setContentText("Nome do Garçom:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nome -> {
            if (!nome.trim().isEmpty()) {
                try {
                    Garcom novoGarcom = new Garcom(nextGarcomId++, nome, null);
                    if (restaurante != null) {
                        restaurante.adicionarGarcom(novoGarcom);
                        carregarGarcons();
                        new Alert(Alert.AlertType.INFORMATION, "Garçom adicionado com sucesso!").showAndWait();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao adicionar garçom: " + e.getMessage()).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "O nome do garçom não pode ser vazio.").showAndWait();
            }
        });
    }

    /**
     * Altera o turno do garçom selecionado na tabela.
     * Exibe mensagens de erro em caso de falha ou seleção inválida.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAlterarTurno(ActionEvent event) {
        Garcom garcomSelecionado = tableViewGarcons.getSelectionModel().getSelectedItem();
        if (garcomSelecionado != null) {
            ChoiceDialog<Turno> dialog = new ChoiceDialog<>(garcomSelecionado.getTurnoAtual(), Turno.values());
            dialog.setTitle("Alterar Turno");
            dialog.setHeaderText("Alterar Turno para " + garcomSelecionado.getNome());
            dialog.setContentText("Escolha o novo turno:");

            Optional<Turno> result = dialog.showAndWait();
            result.ifPresent(turno -> {
                try {
                    garcomSelecionado.setTurnoAtual(turno);
                    tableViewGarcons.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Turno alterado com sucesso!").showAndWait();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao alterar turno: " + e.getMessage()).showAndWait();
                }
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "Selecione um garçom para alterar o turno.").showAndWait();
        }
    }

    /**
     * Volta para o menu principal do sistema.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController menuController = loader.getController();
            menuController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        }
    }
}