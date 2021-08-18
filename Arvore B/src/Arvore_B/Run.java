/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore_B;

import Util.ManiArquivo;
import java.util.Scanner;

/**
 *
 * @author Lucas Reis
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int OP;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Árvore B =====\n");
        
        //=================================================
        System.out.println("\n --- Inserindo ---");
        
        String arquivo = "src\\Entrada\\entrada.txt";
        String[] convert = ManiArquivo.lerArquivo(arquivo);
        int conv = Integer.parseInt(convert[0]);
        
        Arvore arvore = new Arvore(conv);
        
        for(int i = 0; i < convert.length; i++)
            if(i != 0){
                conv = Integer.parseInt(convert[i]);
                arvore.insere(conv);
            }
                
        
        
        //=================================================
        System.out.println("\n --- Exibindo ---");
        arvore.exibir(arvore.getRaiz());
        
        
        //=================================================
        System.out.println("\n --- Buscando ---");
        System.out.println(" Insira a chave de busca: ");
        OP = scanner.nextInt();
        
        if (arvore.buscaChave(arvore.getRaiz(), OP) != null)
            System.out.println("Chave existe na arvore!!!");
        else
            System.out.println("Não foi encontrada a chave informada");
                   
        //=================================================
        System.out.println("\n --- Exibindo ---");
        arvore.exibir(arvore.getRaiz());
        
        
        //=================================================
        System.out.println("\n --- Removendo ---");
        System.out.print("Insira o elemento que deseja remover: ");
        OP = scanner.nextInt();
        
        if (arvore.remove(OP))
            System.out.println("Chave removida com sucesso!");
        else
            System.out.println("Chave não existe!");
        
        
        //=================================================
        System.out.println("\n --- Exibindo ---");
        arvore.exibir(arvore.getRaiz());
        
        //=================================================
        System.out.println("\n\n ENCERRANDO APLICAÇÃO");
    }
}
