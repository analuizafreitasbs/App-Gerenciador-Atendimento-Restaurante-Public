package com.example.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;

import com.example.model.Atendimento;
import com.example.model.AtendimentoGrupo;
import com.example.model.AtendimentoIndividual;
import com.example.model.Atendivel;
import com.example.model.Cliente;
import com.example.model.Garcom;
import com.example.model.GrupoClientes;
import com.example.model.Restaurante;
import com.example.util.Status;
import com.example.util.TipoCliente;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controlador da tela de atendimento do garçom.
 * <p>
 * Gerencia as ações de atendimento, cadastro, finalização e gerenciamento de pedidos de clientes e grupos.
 * Trata exceções de IO e argumentos inválidos, exibindo mensagens apropriadas ao usuário.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Exibir e atualizar a fila de espera geral e os atendimentos do garçom logado.</li>
 *   <li>Permitir ao garçom atender o próximo da fila geral, cadastrar e atender novos clientes ou grupos.</li>
 *   <li>Gerenciar a finalização de atendimentos e o gerenciamento de pedidos.</li>
 *   <li>Permitir reordenar a fila de espera priorizando clientes prioritários.</li>
 *   <li>Gerenciar a navegação entre telas do sistema.</li>
 *   <li>Tratar exceções e exibir mensagens de erro ou sucesso ao usuário.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Restaurante, Garcom, Cliente, GrupoClientes, Atendimento, AtendimentoIndividual, AtendimentoGrupo, Atendivel.</li>
 *   <li>Utilitários: Status, TipoCliente.</li>
 *   <li>JavaFX: ListView, Label, Dialog, ComboBox, Spinner, TextArea, Alert, FXMLLoader, Scene, Stage.</li>
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
public class TelaAtendimentoController {

    /** Referência ao restaurante em uso */
    private Restaurante restaurante;
    /** Garçom atualmente logado */
    private Garcom garcomLogado;

    /** ListView que exibe a fila de espera geral */
    @FXML
    private ListView<String> listViewFilaEspera;
    /** ListView que exibe os atendimentos do garçom logado */
    @FXML
    private ListView<String> listViewAtendimentosDoGarcom;
    /** Label que exibe o nome do garçom logado */
    @FXML
    private Label labelGarcomLogadoNome;

    /** Lista observável da fila de espera */
    private ObservableList<Atendivel> filaDeEspera;
    /** Lista observável de textos para exibição da fila de espera */
    private ObservableList<String> listaTextoFilaEspera;
    /** Lista observável dos atendimentos do garçom */
    private ObservableList<Atendimento> atendimentosDoGarcom;
    /** Lista observável de textos para exibição dos atendimentos do garçom */
    private ObservableList<String> listaTextoAtendimentosDoGarcom;

    /**
     * Define o restaurante e inicializa a fila de espera.
     * @param restaurante Restaurante em uso
     */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        this.filaDeEspera = FXCollections.observableArrayList(restaurante.getFilaDeEsperaGeral());
        this.listaTextoFilaEspera = FXCollections.observableArrayList();
        atualizarListViewFilaEspera();
    }

    /**
     * Define o garçom logado e inicializa suas listas de atendimento.
     * @param garcom Garçom logado
     */
    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
        if (garcomLogado != null) {
            labelGarcomLogadoNome.setText("Garçom: " + garcomLogado.getNome());
            this.atendimentosDoGarcom = FXCollections.observableArrayList();
            this.listaTextoAtendimentosDoGarcom = FXCollections.observableArrayList();
            atualizarListas();
        }
    }

    /**
     * Inicializa os listeners dos ListViews.
     */
    @FXML
    public void initialize() {
        listViewFilaEspera.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> atualizarBotoes());
        listViewAtendimentosDoGarcom.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> atualizarBotoes());
    }

    /**
     * Atualiza o estado dos botões da interface.
     * (Implementação opcional)
     */
    private void atualizarBotoes() {
        // Implementação opcional para habilitar/desabilitar botões
    }

    /**
     * Atualiza a exibição da fila de espera geral.
     */
    private void atualizarListViewFilaEspera() {
        listaTextoFilaEspera.clear();
        if (filaDeEspera != null) {
            filaDeEspera.forEach(atendivel ->
                    listaTextoFilaEspera.add(atendivel.getNome() + " (" + atendivel.getTipoCliente() + ")")
            );
            listViewFilaEspera.setItems(listaTextoFilaEspera);
        }
    }

    /**
     * Atualiza a exibição dos atendimentos do garçom logado.
     */
    private void atualizarListViewAtendimentosDoGarcom() {
        listaTextoAtendimentosDoGarcom.clear();
        if (atendimentosDoGarcom != null) {
            atendimentosDoGarcom.forEach(at -> {
                String nome = at instanceof AtendimentoIndividual ?
                        ((AtendimentoIndividual)at).getCliente().getNome() :
                        ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
                listaTextoAtendimentosDoGarcom.add(nome + " - Status: " + at.getStatus());
            });
            listViewAtendimentosDoGarcom.setItems(listaTextoAtendimentosDoGarcom);
        }
    }

    /**
     * Atualiza as listas de fila de espera e atendimentos do garçom.
     */
    private void atualizarListas() {
        if (restaurante == null || garcomLogado == null) return;

        filaDeEspera.setAll(restaurante.getFilaDeEsperaGeral());
        atendimentosDoGarcom.clear();

        garcomLogado.getFilaAtendimentoIndividual().getFila().stream()
                .filter(at -> at.getStatus() != Status.FINALIZADO)
                .forEach(atendimentosDoGarcom::add);

        garcomLogado.getFilaAtendimentoGrupo().getFila().stream()
                .filter(at -> at.getStatus() != Status.FINALIZADO)
                .forEach(atendimentosDoGarcom::add);

        atualizarListViewFilaEspera();
        atualizarListViewAtendimentosDoGarcom();
    }

    /**
     * Atende o próximo cliente ou grupo da fila geral, se possível.
     * Trata exceções de argumentos inválidos.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleAtenderProximoDaFilaGeral(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para distribuir atendimento.");
            return;
        }
        if (restaurante.getFilaDeEsperaGeral().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Não há clientes ou grupos na fila de espera geral para distribuir.");
            return;
        }

        Atendivel atendivel = restaurante.getFilaDeEsperaGeral().removeFirst();
        try {
            if (atendivel instanceof Cliente cliente) {
                if (garcomLogado.podeAtenderMaisClientesIndividuais()) {
                    cliente.setHoraChegada(LocalTime.now());
                    garcomLogado.atenderCliente(cliente);
                } else {
                    showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de clientes individuais atingido.");
                    restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
                    return;
                }
            } else if (atendivel instanceof GrupoClientes grupo) {
                if (garcomLogado.podeAtenderMaisGrupos()) {
                    grupo.setHoraChegada(LocalTime.now());
                    garcomLogado.atenderGrupo(grupo);
                } else {
                    showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de grupos atingido.");
                    restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
                    return;
                }
            }
            atualizarListas();
            showAlert(Alert.AlertType.INFORMATION, atendivel.getNome() + " adicionado à sua fila de atendimentos.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao atender: " + e.getMessage());
            restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro inesperado ao atender: " + e.getMessage());
            restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
        }
        atualizarListas();
    }

    /**
     * Abre o diálogo para cadastrar e atender um novo cliente individual.
     * Trata exceções inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleCadastrarAtenderNovoCliente(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para cadastrar e atender.");
            return;
        }
        if (!garcomLogado.podeAtenderMaisClientesIndividuais()) {
            showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de clientes individuais atingido.");
            return;
        }

        try {
            Dialog<Cliente> dialog = createClienteDialog();
            Optional<Cliente> result = dialog.showAndWait();

            result.ifPresent(cliente -> {
                try {
                    garcomLogado.atenderCliente(cliente);
                    atualizarListas();
                    showAlert(Alert.AlertType.INFORMATION, "Cliente " + cliente.getNome() + " cadastrado e adicionado à fila.");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erro ao cadastrar cliente: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro inesperado ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Cria o diálogo de cadastro de novo cliente individual.
     * @return Diálogo configurado para cadastro de cliente
     */
    private Dialog<Cliente> createClienteDialog() {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Cadastrar e Atender Novo Cliente");
        dialog.setHeaderText("Informações do Novo Cliente");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar e Atender", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Cliente");
        ComboBox<TipoCliente> tipoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        tipoComboBox.getSelectionModel().selectFirst();
        TextArea observacoesArea = new TextArea();
        observacoesArea.setPromptText("Observações (ex: alergia a glúten)");
        observacoesArea.setWrapText(true);
        observacoesArea.setPrefRowCount(3);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(tipoComboBox, 1, 1);
        grid.add(new Label("Observações:"), 0, 2);
        grid.add(observacoesArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType && !nomeField.getText().trim().isEmpty()) {
                Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(),
                        nomeField.getText().trim(), tipoComboBox.getSelectionModel().getSelectedItem());
                novoCliente.setObservacoesGerais(observacoesArea.getText().trim());
                novoCliente.setHoraChegada(LocalTime.now());
                return novoCliente;
            }
            return null;
        });

        return dialog;
    }

    /**
     * Abre o diálogo para cadastrar e atender um novo grupo de clientes.
     * Trata exceções inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleCadastrarAtenderNovoGrupo(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para cadastrar e atender.");
            return;
        }
        if (!garcomLogado.podeAtenderMaisGrupos()) {
            showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de grupos atingido.");
            return;
        }

        try {
            Dialog<GrupoClientes> dialog = createGrupoDialog();
            Optional<GrupoClientes> result = dialog.showAndWait();

            result.ifPresent(grupo -> {
                try {
                    garcomLogado.atenderGrupo(grupo);
                    atualizarListas();
                    showAlert(Alert.AlertType.INFORMATION, "Grupo " + grupo.getNomeGrupo() + " cadastrado e adicionado à fila.");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erro ao cadastrar grupo: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro inesperado ao cadastrar grupo: " + e.getMessage());
        }
    }

    /**
     * Cria o diálogo de cadastro de novo grupo de clientes.
     * @return Diálogo configurado para cadastro de grupo
     */
    private Dialog<GrupoClientes> createGrupoDialog() {
        Dialog<GrupoClientes> dialog = new Dialog<>();
        dialog.setTitle("Cadastrar e Atender Novo Grupo");
        dialog.setHeaderText("Informações do Novo Grupo");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar e Atender", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeGrupoField = new TextField();
        nomeGrupoField.setPromptText("Nome do Grupo");
        Spinner<Integer> numClientesSpinner = new Spinner<>(1, 100, 1);
        TextArea observacoesArea = new TextArea();
        observacoesArea.setPromptText("Observações (ex: mesa no canto)");
        observacoesArea.setWrapText(true);
        observacoesArea.setPrefRowCount(3);

        grid.add(new Label("Nome do Grupo:"), 0, 0);
        grid.add(nomeGrupoField, 1, 0);
        grid.add(new Label("Número de Pessoas:"), 0, 1);
        grid.add(numClientesSpinner, 1, 1);
        grid.add(new Label("Observações:"), 0, 2);
        grid.add(observacoesArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType && !nomeGrupoField.getText().trim().isEmpty()) {
                GrupoClientes novoGrupo = new GrupoClientes(
                        restaurante.gerarNovoGrupoId(), nomeGrupoField.getText().trim());
                novoGrupo.setObservacoesGerais(observacoesArea.getText().trim());
                for (int i = 0; i < numClientesSpinner.getValue(); i++) {
                    Cliente membro = new Cliente(0, "Membro " + (i + 1), TipoCliente.COMUM);
                    membro.setHoraChegada(LocalTime.now());
                    novoGrupo.adicionarCliente(membro);
                }
                novoGrupo.setHoraChegada(LocalTime.now());
                return novoGrupo;
            }
            return null;
        });

        return dialog;
    }

    /**
     * Reordena a fila de espera priorizando clientes prioritários.
     * Trata exceções inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleReordenarFila(ActionEvent event) {
        try {
            restaurante.getFilaDeEsperaGeral().sort(Comparator
                    .comparing((Atendivel a) -> a.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1)
                    .thenComparing(Atendivel::getNome));
            filaDeEspera.setAll(restaurante.getFilaDeEsperaGeral());
            atualizarListViewFilaEspera();
            showAlert(Alert.AlertType.INFORMATION, "Fila de espera reordenada.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao reordenar fila: " + e.getMessage());
        }
    }

    /**
     * Finaliza o atendimento selecionado na lista do garçom.
     * Trata exceções inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleFinalizarAtendimento(ActionEvent event) {
        try {
            String selectedString = listViewAtendimentosDoGarcom.getSelectionModel().getSelectedItem();
            if (selectedString == null) {
                showAlert(Alert.AlertType.WARNING, "Selecione um atendimento para finalizar.");
                return;
            }

            Atendimento selecionado = atendimentosDoGarcom.stream()
                    .filter(at -> {
                        String nomeExibido = at instanceof AtendimentoIndividual ?
                                ((AtendimentoIndividual)at).getCliente().getNome() :
                                ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
                        String texto = nomeExibido + " - Status: " + at.getStatus();
                        return texto.equals(selectedString) && at.getStatus() != Status.FINALIZADO;
                    })
                    .findFirst()
                    .orElse(null);

            if (selecionado != null) {
                selecionado.finalizarAtendimento();
                restaurante.registrarAtendimentoFinalizado(selecionado);
                atualizarListas();
                showAlert(Alert.AlertType.INFORMATION, "Atendimento finalizado.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Atendimento inválido ou já finalizado.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao finalizar atendimento: " + e.getMessage());
        }
    }

    /**
     * Abre a tela de gerenciamento de pedido para o atendimento selecionado.
     * Trata exceções de IO e outras inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleGerenciarPedido(ActionEvent event) {
        try {
            String selectedString = listViewAtendimentosDoGarcom.getSelectionModel().getSelectedItem();
            if (selectedString == null) {
                showAlert(Alert.AlertType.WARNING, "Selecione um atendimento para gerenciar o pedido.");
                return;
            }

            Atendimento selecionado = atendimentosDoGarcom.stream()
                    .filter(at -> {
                        String nomeExibido = at instanceof AtendimentoIndividual ?
                                ((AtendimentoIndividual)at).getCliente().getNome() :
                                ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
                        String texto = nomeExibido + " - Status: " + at.getStatus();
                        return texto.equals(selectedString) && at.getStatus() == Status.EM_ATENDIMENTO;
                    })
                    .findFirst()
                    .orElse(null);

            if (selecionado != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGerenciadorPedido.fxml"));
                    Parent root = loader.load();

                    TelaGerenciarPedidoController controller = loader.getController();
                    controller.setRestaurante(restaurante);
                    controller.setGarcomLogado(garcomLogado);
                    controller.setAtendimentoAtual(selecionado);

                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gerenciar Pedido");
                    stage.show();
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Erro ao carregar tela de pedido: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Selecione um atendimento em andamento.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao gerenciar pedido: " + e.getMessage());
        }
    }

    /**
     * Volta para a tela anterior (funções do garçom).
     * Trata exceções de IO e outras inesperadas.
     * @param event Evento de ação do botão
     */
    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcomLogado.fxml"));
            Parent root = loader.load();

            TelaGarcomLogadoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            controller.setGarcomLogado(garcomLogado);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Funções do Garçom");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao voltar: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro inesperado ao voltar: " + e.getMessage());
        }
    }

    /**
     * Exibe um alerta na tela.
     * @param type Tipo de alerta
     * @param message Mensagem a ser exibida
     */
    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }
}