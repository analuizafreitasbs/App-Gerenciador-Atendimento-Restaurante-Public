package com.example.model;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.TipoCliente;

/**
 * Classe de teste unitário para a lógica de negócio da {@link Cliente}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de clientes,
 * como validação do construtor, preferências, nome, tipo, hora de chegada e observações gerais.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Validação do construtor para nome e tipo nulos.</li>
 *   <li>Não permite adicionar ou remover preferências nulas.</li>
 *   <li>Adição e remoção de preferências funciona corretamente.</li>
 *   <li>Getters de nome e tipo retornam valores corretos.</li>
 *   <li>Set e get da hora de chegada funcionam corretamente.</li>
 *   <li>Set e get de observações gerais funcionam corretamente.</li>
 *   <li>Lista de preferências é inicialmente vazia e não nula.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link Cliente} para simular o fluxo de uso.</li>
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
class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "João", TipoCliente.COMUM);
    }

    @Test
    void construtorDeveLancarExcecaoSeNomeOuTipoNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(2, null, TipoCliente.COMUM));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(3, "Maria", null));
    }

    @Test
    void adicionarPreferenciaNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cliente.adicionarPreferencia(null));
    }

    @Test
    void removerPreferenciaNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cliente.removerPreferencia(null));
    }

    @Test
    void adicionarERemoverPreferenciaFunciona() {
        cliente.adicionarPreferencia("Sem lactose");
        assertTrue(cliente.getPreferencias().contains("Sem lactose"));
        cliente.removerPreferencia("Sem lactose");
        assertFalse(cliente.getPreferencias().contains("Sem lactose"));
    }

    @Test
    void getNomeRetornaNomeCorreto() {
        assertEquals("João", cliente.getNome());
    }

    @Test
    void getTipoClienteRetornaTipoCorreto() {
        assertEquals(TipoCliente.COMUM, cliente.getTipoCliente());
    }

    @Test
    void setEGetHoraChegadaFunciona() {
        LocalTime agora = LocalTime.now();
        cliente.setHoraChegada(agora);
        assertEquals(agora, cliente.getHoraChegada());
    }

    @Test
    void setEGetObservacoesGeraisFunciona() {
        cliente.setObservacoesGerais("Cliente alérgico a amendoim");
        assertEquals("Cliente alérgico a amendoim", cliente.getObservacoesGerais());
    }

    @Test
    void preferenciasInicialmenteVazia() {
        assertNotNull(cliente.getPreferencias());
        assertTrue(cliente.getPreferencias().isEmpty());
    }
}