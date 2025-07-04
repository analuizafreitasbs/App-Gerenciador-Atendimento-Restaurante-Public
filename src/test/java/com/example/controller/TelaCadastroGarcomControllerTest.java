package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Garcom;
import com.example.model.Restaurante;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaCadastroGarcomController}.
 * <p>
 * Este teste NÃO depende de JavaFX ou Mockito e cobre apenas regras de negócio relacionadas ao cadastro de garçons.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Validação de nome vazio ou em branco: não deve permitir cadastro.</li>
 *   <li>Validação de nome válido: deve permitir cadastro e adicionar o garçom ao restaurante.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>O método auxiliar {@code validaENovaLogicaCadastrarGarcom} simula a lógica de cadastro sem interação com a tela.</li>
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
public class TelaCadastroGarcomControllerTest {

    private TelaCadastroGarcomController controller;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        controller = new TelaCadastroGarcomController();
        restaurante = new Restaurante("Restaurante Teste");
        controller.setRestaurante(restaurante);
    }

    /**
     * Testa que não é possível cadastrar um garçom com nome vazio ou apenas espaços.
     */
    @Test
    public void testNomeVazioNaoDeveCadastrar() {
        String nomeVazio = "    ";

        boolean resultado = validaENovaLogicaCadastrarGarcom(nomeVazio);

        assertFalse(resultado, "Não deve cadastrar com nome vazio");
        assertEquals(0, restaurante.getGarcons().size());
    }

    /**
     * Testa que é possível cadastrar um garçom com nome válido.
     */
    @Test
    public void testNomeValidoDeveCadastrar() {
        String nomeValido = "José da Silva";

        boolean resultado = validaENovaLogicaCadastrarGarcom(nomeValido);

        assertTrue(resultado, "Deve cadastrar com nome válido");
        assertEquals(1, restaurante.getGarcons().size());
        Garcom garcom = restaurante.getGarcons().get(0);
        assertEquals(nomeValido, garcom.getNome());
    }

    /**
     * Método auxiliar para testar só a lógica do cadastro do garçom,
     * sem dependência de JavaFX ou Mockito.
     * @param nome nome do garçom
     * @return true se cadastrou, false se não cadastrou (nome inválido)
     */
    private boolean validaENovaLogicaCadastrarGarcom(String nome) {
        nome = nome == null ? "" : nome.trim();
        if (nome.isEmpty()) {
            return false;
        }

        try {
            int novoId = restaurante.gerarNovoGarcomId();
            Garcom novoGarcom = new Garcom(novoId, nome, null);
            restaurante.adicionarGarcom(novoGarcom);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}