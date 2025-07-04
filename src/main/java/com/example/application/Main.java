package com.example.application;

import com.example.controller.TelaInicialController;
import com.example.model.Cliente;
import com.example.model.Garcom;
import com.example.model.GrupoClientes;
import com.example.model.ItemPedido;
import com.example.model.Restaurante;
import com.example.util.TipoCliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Classe principal da aplicação de Gerenciamento de Atendimento de Restaurante.
 * <p>
 * Responsável por inicializar a aplicação JavaFX, criar dados de exemplo para garçons,
 * clientes, grupos de clientes e itens do cardápio, além de carregar a tela inicial da interface gráfica.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>{@link IOException} - Pode ser lançada ao carregar o arquivo FXML se o recurso não for encontrado ou estiver corrompido.</li>
 *   <li>{@link NullPointerException} - Pode ocorrer se algum recurso, como o FXML ou controlador, não for encontrado corretamente.</li>
 *   <li>Exceções de inicialização de objetos - Caso algum construtor de modelo lance exceção, ela será propagada.</li>
 * </ul>
 *
 * <p><b>Fluxo principal:</b></p>
 * <ol>
 *   <li>Cria o restaurante e popula com dados de exemplo.</li>
 *   <li>Carrega o FXML da tela inicial.</li>
 *   <li>Passa o restaurante para o controlador da tela inicial.</li>
 *   <li>Exibe a janela principal da aplicação.</li>
 * </ol>
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
public class Main extends Application {
    private Restaurante restaurante;

    /**
     * Inicializa a aplicação JavaFX, cria dados de exemplo e exibe a tela inicial.
     *
     * @param primaryStage o palco principal da aplicação JavaFX
     * @throws Exception caso ocorra erro ao carregar o FXML ou inicializar dados
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            restaurante = new Restaurante("Meu Restaurante");
            // Adiciona garçons iniciais para teste (alguns com IDs específicos)
            restaurante.adicionarGarcom(new Garcom(restaurante.gerarNovoGarcomId(), "João Silva", null));
            restaurante.adicionarGarcom(new Garcom(restaurante.gerarNovoGarcomId(), "Maria Oliveira", null));
            restaurante.adicionarGarcom(new Garcom(1001, "Carlos Souza", null)); // Garçom com ID pré-definido

            restaurante.adicionarAoCardapio(new ItemPedido("Pizza Margherita", 1, 45.00));
            restaurante.adicionarAoCardapio(new ItemPedido("Refrigerante Coca-Cola", 1, 7.50));
            restaurante.adicionarAoCardapio(new ItemPedido("Lasanha Bolonhesa", 1, 38.00));

            // Adicionar alguns clientes e grupos à fila de espera geral para teste
            Cliente c1 = new Cliente(restaurante.gerarNovoClienteId(), "Ana Paula", TipoCliente.COMUM);
            c1.setHoraChegada(LocalTime.now().minusMinutes(10));
            restaurante.getFilaDeEsperaGeral().add(c1);

            Cliente c2 = new Cliente(restaurante.gerarNovoClienteId(), "Pedro Souza", TipoCliente.PRIORITARIO);
            c2.setHoraChegada(LocalTime.now().minusMinutes(5));
            restaurante.getFilaDeEsperaGeral().add(c2);

            GrupoClientes g1 = new GrupoClientes(restaurante.gerarNovoGrupoId(), "Família Garcia");
            g1.adicionarCliente(new Cliente(restaurante.gerarNovoClienteId(), "João G.", TipoCliente.COMUM));
            g1.adicionarCliente(new Cliente(restaurante.gerarNovoClienteId(), "Maria G.", TipoCliente.COMUM));
            g1.setHoraChegada(LocalTime.now().minusMinutes(8));
            restaurante.getFilaDeEsperaGeral().add(g1);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            TelaInicialController telaInicialController = fxmlLoader.getController();
            telaInicialController.setRestaurante(restaurante);

            primaryStage.setTitle("Sistema de Gerenciamento de Restaurante");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a interface gráfica: " + e.getMessage());
            throw e;
        } catch (NullPointerException e) {
            System.err.println("Erro de recurso não encontrado ou inicialização nula: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao iniciar a aplicação: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Método principal. Inicia a aplicação JavaFX.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        launch(args);
    }
}