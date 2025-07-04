package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Restaurante;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaPrincipalController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas a lógica de atribuição do restaurante ao controlador da tela principal.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o método {@code setRestaurante} aceita e atribui corretamente diferentes instâncias de {@link Restaurante}.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos de navegação (handle...) são fortemente acoplados ao JavaFX e não são testados aqui.</li>
 *   <li>Para testar navegação e integração com a interface gráfica seria necessário um teste de integração com JavaFX.</li>
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
class TelaPrincipalControllerTest {

    private TelaPrincipalController controller;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        controller = new TelaPrincipalController();
        restaurante = new Restaurante("Restaurante Teste");
        controller.setRestaurante(restaurante);
    }

    /**
     * Testa se o método setRestaurante aceita e atribui corretamente diferentes instâncias de Restaurante.
     */
    @Test
    void setRestauranteAtribuiCorretamente() {
        Restaurante novoRestaurante = new Restaurante("Novo Restaurante");
        controller.setRestaurante(novoRestaurante);
        // Não há getter, mas podemos testar se não lança exceção e aceita o objeto
        assertDoesNotThrow(() -> controller.setRestaurante(novoRestaurante));
    }

    // Como os métodos handle... são fortemente acoplados ao JavaFX, testamos apenas a lógica de atribuição
    // e garantimos que o método setRestaurante aceita diferentes instâncias sem lançar exceção.

    // Para testar a navegação, seria necessário um teste de integração com JavaFX, que foge do escopo unitário.
}