package com.example.util.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Garcom;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Classe utilitária para persistência de dados dos garçons em arquivo JSON.
 * <p>
 * Permite salvar e carregar a lista de garçons do sistema utilizando a biblioteca Gson.
 * Trata exceções de IO e exibe mensagens apropriadas no console.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Salvar a lista de garçons em arquivo JSON.</li>
 *   <li>Carregar a lista de garçons do arquivo JSON.</li>
 *   <li>Tratar exceções de leitura e escrita de arquivos.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Garcom.</li>
 *   <li>Biblioteca: Gson.</li>
 *   <li>Java: FileWriter, FileReader, IOException, List.</li>
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
public class Persistencia {
    /** Caminho do arquivo JSON utilizado para persistência dos garçons */
    private static final String CAMINHO_ARQUIVO = "garcons.json";

    private static class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
        /**
         * Adaptador personalizado para serializar e desserializar objetos do tipo Duration com Gson.
         * Converte Duration para String no formato ISO-8601 ao serializar e reconstrói o objeto ao desserializar.
         */
        @Override
        public JsonElement serialize(Duration src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
        @Override
        public Duration deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Duration.parse(json.getAsString());
        }
    }

    /**
     * Adaptador personalizado para serializar e desserializar objetos do tipo LocalTime com Gson.
     * Converte LocalTime para String no formato padrão ao serializar e reconstrói o objeto ao desserializar.
     */
    private static class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        @Override
        public JsonElement serialize(LocalTime src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public LocalTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalTime.parse(json.getAsString());
        }
    }

    /**
     * Instância de Gson configurada com adaptadores para Duration e LocalTime,
     * permitindo a correta serialização e desserialização desses tipos.
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();

    /**
     * Salva a lista de garçons no arquivo JSON.
     * Trata exceções de IO e exibe mensagens de erro no console.
     *
     * @param garcons Lista de garçons a ser salva
     */
    public static void salvarGarcons(List<Garcom> garcons) {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(garcons, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar garçons: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar garçons: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de garçons do arquivo JSON.
     * Trata exceções de IO e retorna uma lista vazia em caso de erro.
     *
     * @return Lista de garçons carregada do arquivo, ou lista vazia se houver erro
     */
    public static List<Garcom> carregarGarcons() {
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            return gson.fromJson(reader, new TypeToken<List<Garcom>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Erro ao carregar garçons: " + e.getMessage());
            // Retorna lista vazia se o arquivo não existir ou não puder ser lido
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar garçons: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}