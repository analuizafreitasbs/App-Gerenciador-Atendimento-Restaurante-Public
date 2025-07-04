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
import com.example.util.Turno;

/**
 * Classe de teste unitário para a lógica de negócio da {@link Garcom}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas ao gerenciamento de garçons,
 * como validação do construtor, limites de atendimento, atendimento de clientes e grupos, remoção e limpeza de filas,
 * além dos getters e setters.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Validação do construtor para nome nulo ou vazio.</li>
 *   <li>Verifica se pode atender mais clientes individuais e grupos conforme o limite.</li>
 *   <li>Não permite atender cliente ou grupo nulo.</li>
 *   <li>Remove atendimento finalizado das filas corretamente.</li>
 *   <li>Limpa as filas de atendimento corretamente.</li>
 *   <li>Getters retornam valores corretos.</li>
 *   <li>Setter de turno atual altera o valor corretamente.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza instâncias reais de {@link Cliente} e {@link GrupoClientes} para simular o fluxo de uso do garçom.</li>
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
class GarcomTest {

    private Garcom garcom;

    @BeforeEach
    void setUp() {
        garcom = new Garcom(1, "Carlos", Turno.MANHA);
    }

    @Test
    void construtorDeveLancarExcecaoSeNomeNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Garcom(2, null, Turno.NOITE));
        assertThrows(IllegalArgumentException.class, () -> new Garcom(3, "", Turno.NOITE));
        assertThrows(IllegalArgumentException.class, () -> new Garcom(4, "   ", Turno.NOITE));
    }

    @Test
    void podeAtenderMaisClientesIndividuaisRetornaCorreto() {
        assertTrue(garcom.podeAtenderMaisClientesIndividuais());
        // Adiciona 5 atendimentos para atingir o limite
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente(i, "Cliente" + i, TipoCliente.COMUM);
            cliente.setHoraChegada(LocalTime.now());
            garcom.atenderCliente(cliente);
        }
        assertFalse(garcom.podeAtenderMaisClientesIndividuais());
    }

    @Test
    void podeAtenderMaisGruposRetornaCorreto() {
        assertTrue(garcom.podeAtenderMaisGrupos());
        // Adiciona 3 atendimentos para atingir o limite
        for (int i = 0; i < 3; i++) {
            GrupoClientes grupo = new GrupoClientes(i, "Grupo" + i);
            grupo.setHoraChegada(LocalTime.now());
            garcom.atenderGrupo(grupo);
        }
        assertFalse(garcom.podeAtenderMaisGrupos());
    }

    @Test
    void atenderClienteNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> garcom.atenderCliente(null));
    }

    @Test
    void atenderGrupoNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> garcom.atenderGrupo(null));
    }

    @Test
    void removerAtendimentoFinalizadoRemoveDasFilas() {
        Cliente cliente = new Cliente(10, "Remover", TipoCliente.COMUM);
        cliente.setHoraChegada(LocalTime.now());
        garcom.atenderCliente(cliente);
        Atendimento atendimento = garcom.getFilaAtendimentoIndividual().getFila().peek();
        assertNotNull(atendimento, "A fila de atendimento individual está vazia!");
        assertTrue(atendimento instanceof AtendimentoIndividual);
        garcom.removerAtendimentoFinalizado((AtendimentoIndividual) atendimento);
        assertFalse(garcom.getFilaAtendimentoIndividual().getFila().contains(atendimento));
    }

    @Test
    void limparFilasDeAtendimentoEsvaziaFilas() {
        Cliente cliente = new Cliente(11, "Limpar", TipoCliente.COMUM);
        cliente.setHoraChegada(LocalTime.now());
        garcom.atenderCliente(cliente);
        GrupoClientes grupo = new GrupoClientes(12, "GrupoLimpar");
        grupo.setHoraChegada(LocalTime.now());
        garcom.atenderGrupo(grupo);

        garcom.limparFilasDeAtendimento();
        assertEquals(0, garcom.getFilaAtendimentoIndividual().tamanho());
        assertEquals(0, garcom.getFilaAtendimentoGrupo().tamanho());
    }

    @Test
    void gettersRetornamValoresCorretos() {
        assertEquals(1, garcom.getId());
        assertEquals("Carlos", garcom.getNome());
        assertEquals(Turno.MANHA, garcom.getTurnoAtual());
    }

    @Test
    void setTurnoAtualAlteraValor() {
        garcom.setTurnoAtual(Turno.NOITE);
        assertEquals(Turno.NOITE, garcom.getTurnoAtual());
    }
}