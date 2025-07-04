package com.example.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.util.TipoCliente;

/**
 * Classe de teste unitário para a interface {@link Atendivel}.
 * <p>
 * Este teste NÃO depende de JavaFX e cobre apenas regras de negócio relacionadas à implementação dos métodos da interface Atendivel.
 * </p>
 *
 * <b>Cobertura dos testes:</b>
 * <ul>
 *   <li>Verifica se os métodos implementados retornam os valores corretos.</li>
 *   <li>Verifica se os métodos lançam {@link UnsupportedOperationException} quando não implementados.</li>
 * </ul>
 *
 * <b>Observações:</b>
 * <ul>
 *   <li>Os métodos testados são de lógica de negócio, sem dependência de interface gráfica.</li>
 *   <li>Utiliza implementações anônimas da interface para simular diferentes comportamentos.</li>
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
class AtendivelTest {

    /**
     * Testa se os métodos implementados retornam os valores corretos.
     */
    @Test
    void metodosDevemRetornarValoresCorretos() {
        Atendivel atendivel = new Atendivel() {
            @Override
            public String getNome() {
                return "Teste";
            }

            @Override
            public TipoCliente getTipoCliente() {
                return TipoCliente.COMUM;
            }

            @Override
            public List<String> getPreferencias() {
                return Arrays.asList("Sem glúten", "Vegano");
            }
        };

        assertEquals("Teste", atendivel.getNome());
        assertEquals(TipoCliente.COMUM, atendivel.getTipoCliente());
        assertTrue(atendivel.getPreferencias().contains("Sem glúten"));
        assertTrue(atendivel.getPreferencias().contains("Vegano"));
    }

    /**
     * Testa se os métodos lançam UnsupportedOperationException quando não implementados.
     */
    @Test
    void metodosDevemLancarUnsupportedOperationExceptionQuandoNaoImplementados() {
        Atendivel atendivel = new Atendivel() {
            @Override
            public String getNome() {
                throw new UnsupportedOperationException();
            }

            @Override
            public TipoCliente getTipoCliente() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<String> getPreferencias() {
                throw new UnsupportedOperationException();
            }
        };

        assertThrows(UnsupportedOperationException.class, atendivel::getNome);
        assertThrows(UnsupportedOperationException.class, atendivel::getTipoCliente);
        assertThrows(UnsupportedOperationException.class, atendivel::getPreferencias);
    }
}