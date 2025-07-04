package com.example.util;

/**
 * Enumeração que representa o status de um atendimento no restaurante.
 * <p>
 * Pode ser utilizada para controlar o ciclo de vida de um atendimento, indicando se está aguardando, em atendimento ou finalizado.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>Não lança exceções diretamente, mas métodos que utilizam esta enumeração podem lançar {@link NullPointerException}
 *   se um valor nulo for passado onde um {@code Status} é esperado.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Definir o status de um atendimento ao longo do seu ciclo de vida.</li>
 *   <li>Utilizar o status para lógica de controle de filas, relatórios e permissões.</li>
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
public enum Status {
    AGUARDANDO, EM_ATENDIMENTO, FINALIZADO
}