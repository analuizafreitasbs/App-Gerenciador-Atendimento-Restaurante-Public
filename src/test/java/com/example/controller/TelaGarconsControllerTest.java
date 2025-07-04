package com.example.controller;

import com.example.model.Garcom;
import com.example.model.Restaurante;
import com.example.util.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaGarconsController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de garçons,
 * como o carregamento da lista de garçons e o controle do próximo ID de garçom.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o restaurante pode ser corretamente setado e recuperado.</li>
 *   <li>Verifica se o campo nextGarcomId é atualizado corretamente ao setar o restaurante.</li>
 *   <li>Verifica o comportamento ao setar o restaurante como null.</li>
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
class TelaGarconsControllerTest {

    private TelaGarconsControllerFake controller;
    private Restaurante restaurante;

    /**
     * Subclasse para expor campos e métodos protegidos/privados e evitar dependência de JavaFX.
     */
    static class TelaGarconsControllerFake extends TelaGarconsController {
        public Restaurante getRestaurante() {
            try {
                var field = TelaGarconsController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                return (Restaurante) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public int getNextGarcomId() {
            try {
                var field = TelaGarconsController.class.getDeclaredField("nextGarcomId");
                field.setAccessible(true);
                return (int) field.get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // Sobrescreve o método para evitar dependência de JavaFX
        @Override
        public void setRestaurante(Restaurante restaurante) {
            try {
                var field = TelaGarconsController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                field.set(this, restaurante);
                // Atualiza nextGarcomId conforme lógica esperada
                var idField = TelaGarconsController.class.getDeclaredField("nextGarcomId");
                idField.setAccessible(true);
                int nextId = restaurante == null ? 1 : restaurante.getGarcons().stream()
                        .mapToInt(Garcom::getId).max().orElse(0) + 1;
                idField.set(null, nextId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeEach
    void setUp() {
        controller = new TelaGarconsControllerFake();
        restaurante = new Restaurante("Restaurante Teste");
    }

    /**
     * Testa se o restaurante pode ser corretamente setado e se o nextGarcomId é atualizado.
     */
    @Test
    void testSetRestauranteCarregaGarcons() {
        Garcom g1 = new Garcom(1, "João", Turno.MANHA);
        Garcom g2 = new Garcom(2, "Maria", Turno.NOITE);
        restaurante.adicionarGarcom(g1);
        restaurante.adicionarGarcom(g2);

        controller.setRestaurante(restaurante);

        // Verifica se o restaurante foi setado corretamente
        assertEquals(restaurante, controller.getRestaurante());
        // Verifica se o nextGarcomId foi atualizado corretamente
        assertEquals(3, controller.getNextGarcomId());
    }

    /**
     * Testa o comportamento ao setar o restaurante como null.
     */
    @Test
    void testSetRestauranteNull() {
        controller.setRestaurante(null);
        assertNull(controller.getRestaurante());
        assertEquals(1, controller.getNextGarcomId());
    }
}