package com.example.controller;

import java.io.IOException;
import java.util.Optional;

import com.example.model.Restaurante;
import com.example.util.Turno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

/**
 * Controlador da tela de controle de turno do restaurante.
 * <p>
 * Permite iniciar e encerrar turnos, além de voltar ao menu principal.
 * Trata exceções e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Iniciar um novo turno no restaurante.</li>
 *   <li>Encerrar o turno atual do restaurante.</li>
 *   <li>Voltar ao menu principal do sistema.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Turno.</li>
 *   <li>JavaFX: ChoiceDialog, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaTurnoController {

    /** Referência ao restaurante em uso */
    private Restaurante restaurante;

    /**
     * Define o restaurante utilizado pelo controlador.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Inicia um novo turno no restaurante, caso não haja turno ativo.
     * Exibe mensagens de erro ou sucesso ao usuário.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleIniciarTurno(ActionEvent event) {
        if (restaurante == null) {
            new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
            return;
        }

        if (restaurante.getTurnoAtual() != null) {
            new Alert(Alert.AlertType.INFORMATION, "Um turno já está ativo: " + restaurante.getTurnoAtual().name()).showAndWait();
            return;
        }

        ChoiceDialog<Turno> dialog = new ChoiceDialog<>(Turno.MANHA, Turno.values());
        dialog.setTitle("Iniciar Turno");
        dialog.setHeaderText("Selecione o turno para iniciar:");
        dialog.setContentText("Turno:");

        Optional<Turno> result = dialog.showAndWait();
        result.ifPresent(turno -> {
            try {
                restaurante.iniciarTurno(turno);
                new Alert(Alert.AlertType.INFORMATION, "Turno " + turno.name() + " iniciado com sucesso!").showAndWait();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Erro ao iniciar turno: " + e.getMessage()).showAndWait();
            }
        });
    }

    /**
     * Encerra o turno atual do restaurante, caso haja um ativo.
     * Exibe mensagens de erro ou sucesso ao usuário.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleEncerrarTurno(ActionEvent event) {
        if (restaurante == null) {
            new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
            return;
        }

        if (restaurante.getTurnoAtual() == null) {
            new Alert(Alert.AlertType.INFORMATION, "Nenhum turno ativo para encerrar.").showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja encerrar o turno atual (" + restaurante.getTurnoAtual().name() + ")?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                restaurante.encerrarTurno();
                new Alert(Alert.AlertType.INFORMATION, "Turno encerrado com sucesso!").showAndWait();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Erro ao encerrar turno: " + e.getMessage()).showAndWait();
            }
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
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao voltar: " + e.getMessage()).showAndWait();
        }
    }
}