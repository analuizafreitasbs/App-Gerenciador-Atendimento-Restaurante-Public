package com.example.model;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.Status;

/**
 * Classe de teste unitário para a lógica de negócio da {@link FilaDeAtendimento}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento da fila de atendimentos,
 * como adição, remoção, contagem, limpeza e reordenação de atendimentos.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Não permite adicionar atendimento nulo à fila.</li>
 *   <li>Adiciona atendimento corretamente na fila.</li>
 *   <li>Remove atendimento da fila com prioridade correta.</li>
 *   <li>Remove atendimento específico corretamente.</li>
 *   <li>Conta apenas atendimentos ativos (não finalizados).</li>
 *   <li>Limpa a fila corretamente.</li>
 *   <li>Reordena a fila mantendo todos os elementos.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza uma classe concreta interna para testar a classe abstrata {@link Atendimento}.</li>
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
class FilaDeAtendimentoTest {

    private FilaDeAtendimento<Atendimento> fila;
    private Atendimento atendimento1;
    private Atendimento atendimento2;

    // Classe concreta para testar Atendimento
    static class AtendimentoConcreto extends Atendimento {
        public AtendimentoConcreto(Pedido pedido) {
            super(pedido);
        }
    }

    @BeforeEach
    void setUp() {
        fila = new FilaDeAtendimento<>();
        atendimento1 = new AtendimentoConcreto(new Pedido());
        atendimento2 = new AtendimentoConcreto(new Pedido());
    }

    /**
     * Testa se não é permitido adicionar atendimento nulo à fila.
     */
    @Test
    void adicionarAtendimentoNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> fila.adicionarAtendimento(null));
    }

    /**
     * Testa se adicionar atendimento adiciona corretamente na fila.
     */
    @Test
    void adicionarAtendimentoAdicionaNaFila() {
        fila.adicionarAtendimento(atendimento1);
        assertEquals(1, fila.tamanho());
        assertTrue(fila.getFila().contains(atendimento1));
    }

    /**
     * Testa se remover atendimento remove da fila com prioridade correta.
     */
    @Test
    void removerAtendimentoRemoveComPrioridade() {
        atendimento1.iniciarAtendimento(LocalTime.now().minusMinutes(10));
        atendimento2.iniciarAtendimento(LocalTime.now().minusMinutes(5));
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);

        Atendimento removido = fila.removerAtendimento();
        assertNotNull(removido);
        assertEquals(Status.EM_ATENDIMENTO, removido.getStatus());
        assertEquals(1, fila.tamanho());
    }

    /**
     * Testa se remover atendimento específico remove corretamente.
     */
    @Test
    void removerAtendimentoEspecificoRemoveCorretamente() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.removerAtendimentoEspecifico(atendimento1);
        assertFalse(fila.getFila().contains(atendimento1));
        assertEquals(1, fila.tamanho());
    }

    /**
     * Testa se contar atendimentos ativos conta apenas os não finalizados.
     */
    @Test
    void contarAtendimentosAtivosContaSomenteNaoFinalizados() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        atendimento1.iniciarAtendimento(LocalTime.now());
        atendimento1.finalizarAtendimento();
        assertEquals(1, fila.contarAtendimentosAtivos());
    }

    /**
     * Testa se limpar a fila esvazia corretamente.
     */
    @Test
    void limparFilaEsvaziaFila() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.limparFila();
        assertEquals(0, fila.tamanho());
    }

    /**
     * Testa se reordenar a fila mantém todos os elementos.
     */
    @Test
    void reordenarFilaMantemElementos() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.reordenarFila();
        assertEquals(2, fila.tamanho());
        assertTrue(fila.getFila().contains(atendimento1));
        assertTrue(fila.getFila().contains(atendimento2));
    }
}