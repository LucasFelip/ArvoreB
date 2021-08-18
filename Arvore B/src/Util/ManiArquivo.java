package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManiArquivo {
    
    public static String[] lerArquivo(String arquivo){
        Fila<String> fila = new Fila<>();

        try{
           String linha;
           FileReader file = new FileReader(arquivo);
           BufferedReader ler = new BufferedReader(file);
           
           do{
               linha = ler.readLine();
               if (linha != null) 
                   fila.adicionar(linha);
           }while(linha != null);
           
           
        }catch(IOException e){}
        String[] dados = new String[fila.getTamanho()];
        
        for (int i = 0; i < dados.length; i++){
            dados[i] = fila.remover();
        }
        return dados;
    }
    
    public static void escreverArquivo(String arquivo, String[] dados){
        try{
            try (FileWriter escrever = new FileWriter(arquivo)) {
                for (String dado : dados) {
                    escrever.write(dado);
                }
            }
        }catch(IOException e){
            System.out.println(" ERRO ");
        }
    }
    
}
