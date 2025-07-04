package com.example.controller;

import java.io.IOException;

import com.example.model.Garcom;
import com.example.model.Restaurante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador da tela de garçom logado.
 * <p>
 * Permite ao garçom acessar funcionalidades como iniciar atendimento, controlar turno e voltar ao menu principal.
 * Trata exceções de IO e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Exibir o nome do garçom logado.</li>
 *   <li>Navegar para a tela de atendimento do garçom.</li>
 *   <li>Navegar para a tela de controle de turno.</li>
 *   <li>Voltar ao menu principal do sistema.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Garcom.</li>
 *   <li>JavaFX: Label, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaGarcomLogadoController {

    /** Referência ao restaurante em uso */
    private Restaurante restaurante;
    /** Garçom atualmente logado */
    private Garcom garcomLogado;

    /** Label para exibir o nome do garçom logado */
    @FXML
    private Label labelNomeGarcom;

    /**
     * Define o restaurante utilizado pelo controlador.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Define o garçom logado e atualiza o label de nome.
     * @param garcom Garçom logado
     */
    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
        if (garcomLogado != null) {
            labelNomeGarcom.setText("Garçom: " + garcomLogado.getNome());
        }
    }

    /**
     * Navega para a tela de atendimento do garçom logado.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleIniciarAtendimento(ActionEvent event) {
        if (garcomLogado == null) {
            new Alert(Alert.AlertType.WARNING, "Nenhum garçom logado. Por favor, faça login primeiro.").showAndWait();
            return;
        }
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
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Atendimento: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao iniciar atendimento: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Navega para a tela de controle de turno.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleControleTurno(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaTurno.fxml"));
            Parent root = loader.load();

            TelaTurnoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            // Se necessário, pode passar o garçom logado para a tela de turno

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Controle de Turno");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Turno: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar controle de turno: " + e.getMessage()).showAndWait();
        }
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