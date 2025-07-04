package com.example.model;

import com.example.util.TipoCliente;
import java.util.List;

/**
 * Interface que define o contrato para entidades que podem ser atendidas no restaurante.
 * <p>
 * Implementações típicas incluem {@link Cliente} e {@link GrupoClientes}.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>As implementações podem lançar {@link NullPointerException} ou {@link UnsupportedOperationException}
 *   dependendo da lógica dos métodos sobrescritos.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Implementar esta interface em classes que representam clientes ou grupos de clientes.</li>
 *   <li>Utilizar os métodos para acessar informações relevantes para o atendimento.</li>
 * </ol>
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
public interface Atendivel {
    /**
     * Retorna o nome do cliente ou grupo.
     *
     * @return nome
     * @throws UnsupportedOperationException se a implementação não suportar esta operação
     */
    String getNome();

    /**
     * Retorna o tipo do cliente (comum, prioritário, etc).
     *
     * @return tipo do cliente
     * @throws UnsupportedOperationException se a implementação não suportar esta operação
     */
    TipoCliente getTipoCliente();

    /**
     * Retorna a lista de preferências do cliente ou grupo.
     *
     * @return lista de preferências
     * @throws UnsupportedOperationException se a implementação não suportar esta operação
     */
    List<String> getPreferencias();
}