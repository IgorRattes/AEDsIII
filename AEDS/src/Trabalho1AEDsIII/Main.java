package Trabalho1AEDsIII;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Trabalho Prático AEDs III
 *
 * Alunos: Chrystian Henrique Fonseca de Souza & Igor Gomes Silva Rattes
 *
 */

public class Main {
    //Mostrando o endereço do arquivo txt
    private static final String CSV_FILE_PATH = "C://Users//igorr//IdeaProjects//AEDS//MoviesTopRated.csv/";
    private static final String DB_FILE_PATH = "MoviesTopRated.db";
    public static String DB_TEMP = "temp.db";
    public static int numerodelinhas;
    public static void main(String[] args) throws IOException, ParseException {
        Scanner leitor = new Scanner(System.in);
        BufferedReader Leitor = null;
        //Ler os arquivos do CSV
        List<Filme> MoviesTopRated = LeitorCSV();
        Filme filme = new Filme();
        filme.ArmzArquivoDB(MoviesTopRated, DB_FILE_PATH);
        int opcao =1;
        //Menu do Programa
        do {
            System.out.println("selecione a operacao CRUD");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("5 - Mostra Registro");
            opcao = leitor.nextInt();
            switch(opcao) {
                //opção para a criação
                case 1:

                    break;
                //opção para a leitura
                case 2:

                    break;
                //opção para a Atualização
                case 3:

                    break;
                //opção para excluir
                case 4:

                    break;
                case 5:
                    /*int quantidade;
                    System.out.println("Quantos registros devem ser exibidos:");
                    quantidade = leitor.nextInt();
                    if(quantidade<=Filme.TamanhoArqCSV(CSV_FILE_PATH)){
                        Filme.MostraDadoDB(CSV_FILE_PATH,quantidade);
                    }*/
                    break;
            }
            System.out.println();
        }while (opcao !=0);
        leitor.close();
    }
    private static List<Filme> LeitorCSV() {
        //Cria uma Lista com os filmes do arquivo CSV
        List<Filme> MoviesTopRated = new ArrayList<>();

        //Leitor de arquivo CSV
        CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';');
        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, csvFormat)) {

            for (CSVRecord csvRecord : csvParser) {
                //Arquiva a String do ID
                String IDString = csvRecord.get(0);
                String popularityString = csvRecord.get(4);
                String voteaverageString = csvRecord.get(6);
                String votecounttString = csvRecord.get(7);

                // converte as string do CSV para inteiro e float
                int id, quantidadedevotos;
                float mediadevotos, Popularidade;
                try {
                    id = Integer.parseInt(IDString);
                } catch (NumberFormatException e) {
                    System.err.println("Não foi possivel converter o ID: " + IDString);
                    continue;
                }
                try {
                    Popularidade = Float.parseFloat(popularityString);
                } catch (NumberFormatException e) {
                    System.err.println("Não foi possivel converter a Popularidade: " + popularityString);
                    continue;
                }
                try {
                    quantidadedevotos = Integer.parseInt(votecounttString);
                } catch (NumberFormatException e) {
                    System.err.println("Não foi possivel converter a Quantidade de Votos: " + votecounttString);
                    continue;
                }
                try {
                    mediadevotos = Float.parseFloat(voteaverageString);
                } catch (NumberFormatException e) {
                    System.err.println("Não foi possivel converter a Média de Votos: " + voteaverageString);
                    continue;
                }
                //As que forem string permanecem como estão
                String title = csvRecord.get(2);
                String overview = csvRecord.get(3);
                String releasedate = csvRecord.get(5);


                // Cria um objeto com os dados lidos
                Filme newFilme = new Filme(id, title, overview, Popularidade, releasedate, mediadevotos, quantidadedevotos);
                MoviesTopRated.add(newFilme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Retorna o objeto
        return MoviesTopRated;
    }

}