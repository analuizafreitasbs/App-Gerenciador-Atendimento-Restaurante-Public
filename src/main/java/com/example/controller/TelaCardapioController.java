package com.example.controller;

import java.io.IOException;
import java.util.Optional;

import com.example.model.ItemPedido;
import com.example.model.Restaurante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controlador da tela de cardápio do restaurante.
 * <p>
 * Permite visualizar, filtrar, adicionar e remover itens do cardápio.
 * Trata exceções de IO, validação e exibe mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Exibir e filtrar itens do cardápio.</li>
 *   <li>Adicionar novos itens ao cardápio com validação de dados.</li>
 *   <li>Remover itens do cardápio com confirmação do usuário.</li>
 *   <li>Navegar de volta ao menu principal.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, ItemPedido.</li>
 *   <li>JavaFX: ListView, TextField, Alert, Dialog, Spinner, FXMLLoader, Scene, Stage.</li>
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
public class TelaCardapioController {

    /** ListView que exibe os itens do cardápio */
    @FXML
    private ListView<String> listaItens;
    /** Campo de texto para filtro de itens */
    @FXML
    private TextField filtro;

    /** Lista observável dos itens do cardápio */
    private ObservableList<ItemPedido> itensCardapio;
    /** Referência ao restaurante em uso */
    private Restaurante restaurante;

    /**
     * Define o restaurante utilizado pelo controlador e carrega os itens do cardápio.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (this.restaurante != null) {
            carregarItensCardapio();
        }
    }

    /**
     * Inicializa a lista de itens e o listener do filtro.
     */
    @FXML
    public void initialize() {
        itensCardapio = FXCollections.observableArrayList();
        filtro.textProperty().addListener((observable, oldValue, newValue) -> filtrarItens(newValue));
    }

    /**
     * Carrega os itens do cardápio do restaurante para a lista observável.
     */
    private void carregarItensCardapio() {
        if (restaurante != null) {
            itensCardapio.clear();
            itensCardapio.addAll(restaurante.getCardapio());
            atualizarListaExibida();
        }
    }

    /**
     * Atualiza a exibição da lista de itens do cardápio.
     */
    private void atualizarListaExibida() {
        listaItens.getItems().clear();
        itensCardapio.stream()
                .map(item -> item.getNome() + " - R$" + String.format("%.2f", item.getPreco()))
                .forEach(itemString -> listaItens.getItems().add(itemString));
    }

    /**
     * Filtra os itens do cardápio conforme o texto digitado no campo de filtro.
     * @param textoFiltro Texto para filtrar os itens
     */
    private void filtrarItens(String textoFiltro) {
        if (textoFiltro == null || textoFiltro.isEmpty()) {
            atualizarListaExibida();
        } else {
            listaItens.getItems().clear();
            itensCardapio.stream()
                    .filter(item -> item.getNome().toLowerCase().contains(textoFiltro.toLowerCase()))
                    .map(item -> item.getNome() + " - R$" + String.format("%.2f", item.getPreco()))
                    .forEach(itemString -> listaItens.getItems().add(itemString));
        }
    }

    /**
     * Abre um diálogo para adicionar um novo item ao cardápio.
     * Valida os dados e trata exceções de conversão e campos obrigatórios.
     * Exibe mensagens de erro ou sucesso ao usuário.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAdicionarItem(ActionEvent event) {
        Dialog<ItemPedido> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Item ao Cardápio");
        dialog.setHeaderText("Novo Item");

        ButtonType adicionarButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(adicionarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Item");
        TextField precoField = new TextField();
        precoField.setPromptText("Preço");
        Spinner<Integer> quantidadeSpinner = new Spinner<>(1, 999, 1);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Preço:"), 0, 1);
        grid.add(precoField, 1, 1);
        grid.add(new Label("Quantidade (inicial):"), 0, 2);
        grid.add(quantidadeSpinner, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == adicionarButtonType) {
                try {
                    String nome = nomeField.getText().trim();
                    if (nome.isEmpty()) {
                        new Alert(Alert.AlertType.WARNING, "O nome do item é obrigatório.").showAndWait();
                        return null;
                    }
                    double preco = Double.parseDouble(precoField.getText().trim());
                    int quantidade = quantidadeSpinner.getValue();
                    if (preco < 0) {
                        new Alert(Alert.AlertType.WARNING, "O preço não pode ser negativo.").showAndWait();
                        return null;
                    }
                    if (quantidade <= 0) {
                        new Alert(Alert.AlertType.WARNING, "A quantidade deve ser maior que zero.").showAndWait();
                        return null;
                    }
                    return new ItemPedido(nome, quantidade, preco);
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Preço inválido. Use um número.").showAndWait();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao adicionar item: " + e.getMessage()).showAndWait();
                }
            }
            return null;
        });

        try {
            Optional<ItemPedido> result = dialog.showAndWait();
            result.ifPresent(item -> {
                try {
                    if (restaurante != null) {
                        restaurante.adicionarAoCardapio(item);
                        carregarItensCardapio();
                        new Alert(Alert.AlertType.INFORMATION, "Item '" + item.getNome() + "' adicionado ao cardápio.").showAndWait();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao adicionar item ao cardápio: " + e.getMessage()).showAndWait();
                }
            });
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao adicionar item: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Remove o item selecionado do cardápio após confirmação do usuário.
     * Trata exceções e exibe mensagens apropriadas.
     *
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleRemoverItem(ActionEvent event) {
        try {
            String selectedItemString = listaItens.getSelectionModel().getSelectedItem();
            if (selectedItemString != null) {
                String nomeItem = selectedItemString.substring(0, selectedItemString.indexOf(" - R$"));

                ItemPedido itemParaRemover = itensCardapio.stream()
                        .filter(item -> item.getNome().equals(nomeItem))
                        .findFirst()
                        .orElse(null);

                if (itemParaRemover != null) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja remover '" + itemParaRemover.getNome() + "' do cardápio?");
                    Optional<ButtonType> result = confirm.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            // Implementação da remoção no restaurante
                            // restaurante.removerItemDoCardapio(itemParaRemover);
                            itensCardapio.remove(itemParaRemover);
                            atualizarListaExibida();
                            new Alert(Alert.AlertType.INFORMATION, "Item removido do cardápio.").showAndWait();
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR, "Erro ao remover item: " + e.getMessage()).showAndWait();
                        }
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione um item para remover.").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro inesperado ao remover item: " + e.getMessage()).showAndWait();
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