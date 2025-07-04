package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Garcom;
import com.example.model.Restaurante;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaLoginGarcomController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas a lógica de validação de login de garçom no restaurante.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se o método {@code validarLoginGarcom} retorna o garçom correto para id e nome válidos.</li>
 *   <li>Verifica se retorna null para id ou nome incorretos.</li>
 *   <li>Verifica se não lança exceção para entradas inválidas (id negativo ou nome null).</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>O teste cobre apenas a validação de login, não a navegação de telas.</li>
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
class TelaLoginGarcomControllerTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
        restaurante.adicionarGarcom(new Garcom(1, "João", null));
    }

    /**
     * Testa se o método validarLoginGarcom retorna o garçom correto para id e nome válidos.
     */
    @Test
    void validarLoginGarcomRetornaGarcomCorreto() {
        Garcom garcom = restaurante.validarLoginGarcom(1, "João");
        assertNotNull(garcom);
        assertEquals(1, garcom.getId());
        assertEquals("João", garcom.getNome());
    }

    /**
     * Testa se o método validarLoginGarcom retorna null para id ou nome incorretos.
     */
    @Test
    void validarLoginGarcomRetornaNullParaIdOuNomeIncorretos() {
        assertNull(restaurante.validarLoginGarcom(2, "João"));
        assertNull(restaurante.validarLoginGarcom(1, "Maria"));
        assertNull(restaurante.validarLoginGarcom(999, "Inexistente"));
    }

    /**
     * Testa se o método validarLoginGarcom não lança exceção para entradas inválidas.
     */
    @Test
    void validarLoginGarcomNaoLancaExcecaoParaEntradasInvalidas() {
        assertDoesNotThrow(() -> restaurante.validarLoginGarcom(-1, null));
        assertNull(restaurante.validarLoginGarcom(-1, null));
    }
}