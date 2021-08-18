/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho_ArvoreB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Lucas Reis
 */
public class No {
    private List<Integer> listChaves = new ArrayList<Integer>();
    private List<No> listFilhos = new ArrayList<No>();
    private int X;
    private int Y;
    
    public List<No> getListFilhos() {
        return listFilhos;
    }
    
    public void addChave(int aChave) {
        listChaves.add(aChave);
    }
    
    public void addChaveNoIndice(int index, int aChave) {
        listChaves.add(index, aChave);
    }
    
    public void addFilhosNoIndice(int index, No aNo) {
        listFilhos.add(index, aNo);
    }
    
    public int getChave(int index) {
        return listChaves.get(index);
    }
    
    public void addFilho(No aNo) {
        listFilhos.add(aNo);
    }
    
    //=============== REMOVE ===============
    public void removeChavePeloIndice(int index) {
        this.listChaves.remove(index);
    }

    public void removeChavePeloValor(int aChave) {
        if (this.listChaves.contains(aChave)) {
            int i = 0;
            while (aChave != this.listChaves.get(i)) {
                i++;
            }
            this.listChaves.remove(i);
        }
    }
    
    public void removeFilho(int index) {
        listFilhos.remove(index);
    }
    
    public int numChaves() {
        return listChaves.size();
    }

    public int numFilhos() {
        return listFilhos.size();
    }
    
    public Iterator getIteratorChaves() {
        return listChaves.iterator();
    }
    
    public boolean contemChave(int aChave) {
        return this.listChaves.contains(aChave);
    }
    
    public int maiorChave() {
        return this.listChaves.get(this.listChaves.size() - 1);
    }
    
    public int menorChave() {
        return this.listChaves.get(0);
    }
    
    public void ordenarNo() {
        Collections.sort(this.listChaves);
    }
    
    public No getFilho(int index) {
        return this.listFilhos.get(index);
    }
    
    public boolean folha() {
        if (this.listFilhos.isEmpty())
            return true;
        
        return false;
    }
    
    public No filhoEsquerdo(int aChave) {
        return (this.getListFilhos().get(this.getIndexChave(aChave)));
    }

    public No filhoDireito(int aChave) {
        return (this.getListFilhos().get(this.getIndexChave(aChave) + 1));
    }
    
     public int getIndexChave(int aChave) {
        int i;
        for (i = 0; i < this.getListChaves().size(); i++) {
            if (this.getListChaves().get(i) == aChave)
                return i;
        }
        return -1;
    }
     
    public No getAntecessor(int aChave) {
        return this.noAntecessor(this.getFilho(this.getIndexChave(aChave)));
    }
    
    public No noAntecessor(No node) {
        if (node.folha()) 
            return node;
         else 
            return node.noAntecessor(node.getFilho(node.getListFilhos().size() - 1));
    }
    
    public No getSucessor(int aChave) {
        return this.noAntecessor(this.getFilho(this.getIndexChave(aChave + 1)));
    }
    
    public No noSucessor(No node) {
        if (node.folha()) 
            return node;
         else 
            return node.noSucessor(node.getFilho(0));
    }
    
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int s) {
        X = s;
    }

    public void setY(int s) {
        Y = s;
    }

    public List<Integer> getListChaves() {
        return listChaves;
    }

    public void setListChaves(List<Integer> listChaves) {
        this.listChaves = listChaves;
    }
    
}
