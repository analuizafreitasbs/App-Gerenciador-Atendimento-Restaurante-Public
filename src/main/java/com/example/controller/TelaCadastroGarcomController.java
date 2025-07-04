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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador da tela de cadastro de garçom.
 * <p>
 * Permite cadastrar um novo garçom no sistema, validando o nome e gerando um novo ID.
 * Após o cadastro, retorna para a tela de login do garçom.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Validar o nome do garçom antes do cadastro.</li>
 *   <li>Gerar um novo ID para o garçom usando o restaurante.</li>
 *   <li>Adicionar o novo garçom ao restaurante.</li>
 *   <li>Exibir mensagens de sucesso ou erro ao usuário.</li>
 *   <li>Navegar entre a tela de cadastro e a tela de login do garçom.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Garcom.</li>
 *   <li>JavaFX: TextField, Alert, FXMLLoader, Scene, Stage.</li>
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

public class TelaCadastroGarcomController {

    /** Campo de texto para o nome completo do garçom */
    @FXML
    private TextField nomeCompletoField;

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
     * Realiza o cadastro de um novo garçom.
     * Valida o nome, gera um novo ID, adiciona ao restaurante e retorna para a tela de login.
     * Exibe mensagens de erro em caso de falha.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleCadastrar(ActionEvent event) {
        String nome = nomeCompletoField.getText().trim();

        if (nome.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, digite o nome completo do garçom.").showAndWait();
            return;
        }

        try {
            int novoId = restaurante.gerarNovoGarcomId(); // Usa o gerador de ID do Restaurante
            Garcom novoGarcom = new Garcom(novoId, nome, null); // Inicia sem turno
            restaurante.adicionarGarcom(novoGarcom);

            new Alert(Alert.AlertType.INFORMATION, "Garçom " + nome + " cadastrado com sucesso!\nSeu ID de Login é: " + novoId).showAndWait();

            // Volta para a tela de login após o cadastro
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
                new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de login.").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar garçom: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Volta para a tela de login do garçom.
     * Exibe mensagem de erro caso não seja possível carregar a tela.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVoltarLogin(ActionEvent event) {
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
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para a tela de login.").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao voltar: " + e.getMessage()).showAndWait();
        }
    }
}