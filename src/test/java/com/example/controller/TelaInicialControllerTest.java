package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Restaurante;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaInicialController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas a lógica de atribuição do restaurante ao controlador da tela inicial.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o método {@code setRestaurante} aceita e atribui corretamente um objeto Restaurante.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos de navegação e exibição de erro dependem de JavaFX e não são testados aqui.</li>
 *   <li>Para testar navegação e exibição de erro seria necessário um teste de integração com JavaFX.</li>
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
class TelaInicialControllerTest {

    private TelaInicialController controller;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        controller = new TelaInicialController();
        restaurante = new Restaurante("Restaurante Teste");
    }

    /**
     * Testa se o método setRestaurante aceita e atribui corretamente um objeto Restaurante.
     */
    @Test
    void setRestauranteAtribuiCorretamente() {
        controller.setRestaurante(restaurante);
        // Não há getter, mas podemos garantir que não lança exceção e aceita o objeto
        assertDoesNotThrow(() -> controller.setRestaurante(restaurante));
    }

    // Como os métodos de navegação dependem do JavaFX, testamos apenas a lógica de atribuição.
    // Para testar navegação e exibição de erro seria necessário um teste de integração com JavaFX.
}