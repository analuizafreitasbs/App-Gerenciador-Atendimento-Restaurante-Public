package com.example.controller;

import com.example.model.Restaurante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador da tela inicial do sistema.
 * <p>
 * Permite iniciar o sistema e navegar para o menu principal.
 * Trata exceções de IO e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Receber a instância do restaurante.</li>
 *   <li>Navegar para o menu principal do sistema.</li>
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
public class TelaInicialController {
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
     * Inicia o sistema e navega para o menu principal.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleIniciar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            showError("Erro ao carregar o menu principal: " + e.getMessage());
        }
    }

    /**
     * Exibe uma mensagem de erro ao usuário.
     * @param message Mensagem de erro
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}