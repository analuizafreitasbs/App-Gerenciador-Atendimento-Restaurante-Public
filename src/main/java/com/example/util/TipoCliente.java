package com.example.util;

/**
 * Enumeração que representa os tipos de clientes do restaurante.
 * <p>
 * Pode ser utilizada para diferenciar o atendimento de clientes prioritários e comuns.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>Não lança exceções diretamente, mas métodos que utilizam esta enumeração podem lançar {@link NullPointerException}
 *   se um valor nulo for passado onde um {@code TipoCliente} é esperado.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Definir o tipo de um cliente ao instanciá-lo.</li>
 *   <li>Utilizar o tipo para lógica de prioridade em filas ou atendimentos.</li>
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
public enum TipoCliente {
    PRIORITARIO, COMUM
}