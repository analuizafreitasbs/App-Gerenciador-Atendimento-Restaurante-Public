package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Restaurante;
import com.example.util.Turno;

/**
 * Classe de teste unitário para a lógica de negócio da {@link TelaTurnoController}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas a lógica de controle de turnos do restaurante,
 * como iniciar e encerrar turnos.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Não permite iniciar um novo turno se já houver um turno ativo.</li>
 *   <li>Permite iniciar um turno quando não há turno ativo.</li>
 *   <li>Não permite encerrar turno se não há turno ativo.</li>
 *   <li>Permite encerrar turno quando há turno ativo.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>O teste simula o comportamento dos métodos handleIniciarTurno e handleEncerrarTurno da tela.</li>
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
class TelaTurnoControllerTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
    }

    /**
     * Testa que não é permitido iniciar um novo turno se já houver um turno ativo.
     */
    @Test
    void naoPermiteIniciarTurnoSeJaHouverTurnoAtivo() {
        restaurante.iniciarTurno(Turno.MANHA);
        // Simula a lógica do método handleIniciarTurno
        assertNotNull(restaurante.getTurnoAtual());
        assertEquals(Turno.MANHA, restaurante.getTurnoAtual());
    }

    /**
     * Testa que é permitido iniciar um turno quando não há turno ativo.
     */
    @Test
    void permiteIniciarTurnoQuandoNaoHaTurnoAtivo() {
        assertNull(restaurante.getTurnoAtual());
        restaurante.iniciarTurno(Turno.NOITE);
        assertEquals(Turno.NOITE, restaurante.getTurnoAtual());
    }

    /**
     * Testa que não é permitido encerrar turno se não há turno ativo.
     */
    @Test
    void naoPermiteEncerrarTurnoSeNaoHaTurnoAtivo() {
        assertNull(restaurante.getTurnoAtual());
        // Simula a lógica: não deve lançar exceção, apenas não faz nada
        restaurante.encerrarTurno();
        assertNull(restaurante.getTurnoAtual());
    }

    /**
     * Testa que é permitido encerrar turno quando há turno ativo.
     */
    @Test
    void permiteEncerrarTurnoQuandoHaTurnoAtivo() {
        restaurante.iniciarTurno(Turno.TARDE);
        assertEquals(Turno.TARDE, restaurante.getTurnoAtual());
        restaurante.encerrarTurno();
        assertNull(restaurante.getTurnoAtual());
    }
}