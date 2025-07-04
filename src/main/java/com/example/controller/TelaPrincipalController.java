package com.example.controller;

import java.io.IOException;

import com.example.model.Restaurante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador da tela principal do sistema.
 * <p>
 * Permite navegar para as principais funcionalidades do sistema: gerenciamento de garçons, cardápio, login de garçom e opções do cliente.
 * Trata exceções de IO e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Receber a instância do restaurante.</li>
 *   <li>Navegar para as telas de gerenciamento de garçons, cardápio, login de garçom e opções do cliente.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante.</li>
 *   <li>JavaFX: Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaPrincipalController {
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
     * Navega para a tela de gerenciamento de garçons.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleGerenciarGarcons(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcons.fxml"));
            Parent root = loader.load();

            TelaGarconsController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gerenciar Garçons");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Garçons: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar gerenciamento de garçons: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Navega para a tela de visualização do cardápio.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVisualizarCardapio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCardapio.fxml"));
            Parent root = loader.load();

            TelaCardapioController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cardápio");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Cardápio: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar cardápio: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Navega para a tela de login do garçom.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleSouGarcom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLoginGarcom.fxml"));
            Parent root = loader.load();

            TelaLoginGarcomController loginController = loader.getController();
            loginController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login do Garçom");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de login do garçom: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar login do garçom: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Navega para a tela de opções do cliente.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleSouCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCliente.fxml"));
            Parent root = loader.load();

            TelaClienteController clienteController = loader.getController();
            clienteController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Opções do Cliente");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela do cliente: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar opções do cliente: " + e.getMessage()).showAndWait();
        }
    }
}