package com.example.util;

/**
 * Enumeração que representa os turnos de funcionamento do restaurante.
 * <p>
 * Pode ser utilizada para controlar o turno de garçons, horários de atendimento e lógica de funcionamento.
 * </p>
 *
 * <p><b>Exceções possíveis:</b></p>
 * <ul>
 *   <li>Não lança exceções diretamente, mas métodos que utilizam esta enumeração podem lançar {@link NullPointerException}
 *   se um valor nulo for passado onde um {@code Turno} é esperado.</li>
 * </ul>
 *
 * <p><b>Uso típico:</b></p>
 * <ol>
 *   <li>Definir o turno de um garçom ou do restaurante.</li>
 *   <li>Utilizar o turno para lógica de controle de horários e permissões.</li>
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
public enum Turno {
    MANHA, TARDE, NOITE
}