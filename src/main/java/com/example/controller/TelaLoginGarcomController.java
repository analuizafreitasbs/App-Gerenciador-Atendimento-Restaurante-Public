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
 * Controlador da tela de login do garçom.
 * <p>
 * Permite ao garçom realizar login, acessar o cadastro de novos garçons e voltar ao menu principal.
 * Trata exceções de IO, validação e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Validar o login do garçom pelo ID e nome.</li>
 *   <li>Navegar para a tela de funções do garçom logado.</li>
 *   <li>Navegar para a tela de cadastro de garçom.</li>
 *   <li>Voltar ao menu principal do sistema.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
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
public class TelaLoginGarcomController {

    /** Campo de texto para o ID do garçom */
    @FXML
    private TextField idField;
    /** Campo de texto para o nome do garçom */
    @FXML
    private TextField nomeField;

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
     * Realiza o login do garçom validando ID e nome.
     * Exibe mensagens de erro em caso de falha.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nome = nomeField.getText().trim();

            Garcom garcomLogado = restaurante.validarLoginGarcom(id, nome);

            if (garcomLogado != null) {
                new Alert(Alert.AlertType.INFORMATION, "Login realizado com sucesso! Bem-vindo(a), " + garcomLogado.getNome() + ".").showAndWait();

                // Redireciona para a nova tela de funções do garçom logado
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcomLogado.fxml"));
                Parent root = loader.load();

                TelaGarcomLogadoController garcomLogadoController = loader.getController();
                garcomLogadoController.setRestaurante(restaurante);
                garcomLogadoController.setGarcomLogado(garcomLogado); // Passa o garçom logado

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Funções do Garçom: " + garcomLogado.getNome());
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "ID ou Nome do garçom inválidos.").showAndWait();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Por favor, digite um ID válido (apenas números).").showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar as funções do garçom: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao realizar login: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Navega para a tela de cadastro de garçom.
     * Trata exceções de IO e exibe mensagens de erro.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleRegistrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastroGarcom.fxml"));
            Parent root = loader.load();

            TelaCadastroGarcomController cadastroController = loader.getController();
            cadastroController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de Garçom");
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de cadastro: " + e.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao acessar cadastro: " + e.getMessage()).showAndWait();
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