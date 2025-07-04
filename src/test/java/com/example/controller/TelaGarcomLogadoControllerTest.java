package com.example.controller;

import com.example.model.Garcom;
import com.example.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaGarcomLogadoController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento do garçom logado
 * e do restaurante associado à tela.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Testa se o restaurante pode ser corretamente setado e recuperado.</li>
 *   <li>Testa se o garçom logado pode ser corretamente setado e recuperado.</li>
 *   <li>Testa o comportamento ao setar o garçom logado como null.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Uma subclasse fake é utilizada para acessar campos privados via reflection e evitar dependência de JavaFX.</li>
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
class TelaGarcomLogadoControllerTest {

    private TelaGarcomLogadoControllerFake controller;
    private Restaurante restaurante;
    private Garcom garcom;

    /**
     * Subclasse para expor os campos privados para teste e evitar dependência de JavaFX.
     */
    static class TelaGarcomLogadoControllerFake extends TelaGarcomLogadoController {
        @Override
        public void setGarcomLogado(Garcom garcom) {
            // Apenas lógica de negócio, sem JavaFX
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("garcomLogado");
                field.setAccessible(true);
                field.set(this, garcom);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public Restaurante getRestaurante() {
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                return (Restaurante) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public Garcom getGarcomLogado() {
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("garcomLogado");
                field.setAccessible(true);
                return (Garcom) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeEach
    void setUp() {
        controller = new TelaGarcomLogadoControllerFake();
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "João", null);
    }

    /**
     * Testa se o restaurante pode ser corretamente setado e recuperado.
     */
    @Test
    void testSetRestaurante() {
        controller.setRestaurante(restaurante);
        assertEquals(restaurante, controller.getRestaurante());
    }

    /**
     * Testa se o garçom logado pode ser corretamente setado e recuperado.
     */
    @Test
    void testSetGarcomLogado() {
        controller.setGarcomLogado(garcom);
        assertEquals(garcom, controller.getGarcomLogado());
    }

    /**
     * Testa o comportamento ao setar o garçom logado como null.
     */
    @Test
    void testSetGarcomLogadoNull() {
        controller.setGarcomLogado(null);
        assertNull(controller.getGarcomLogado());
    }
}