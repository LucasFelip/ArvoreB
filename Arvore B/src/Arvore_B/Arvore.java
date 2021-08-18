/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arvore_B;

/**
 *
 * @author Lucas Reis
 */
public class Arvore {
    private No raiz;
    private final int iNumMaxFilhos;
    private final int iNumMinChaves;
    private int iNumMaxChaves;
    
    public Arvore(int aNumMaxFilhos){
        iNumMaxFilhos = aNumMaxFilhos;
        iNumMinChaves = (int) Math.floor(((double) (iNumMaxFilhos - 1)) / ((double) 2));
        iNumMaxChaves = iNumMaxFilhos - 1;
        raiz = new No();
    }
    
    public boolean vazia() {
        if (raiz.numChaves() == 0)
            return true;
        return false;
    }
    
    public boolean insere(int aChave){
        if(buscaChave(raiz, aChave) != null) 
            return false;
        else{
            if(raiz.numChaves() < iNumMaxChaves) {
                if(raiz.folha()) {
                    raiz.addChave(aChave);
                    raiz.ordenarNo();
                }else 
                    insereNoNaoCheio(raiz, aChave);
            }
            else{
                if(raiz.numChaves() == iNumMaxChaves) {
                    if(raiz.folha()) {
                        No noFilho = raiz;
                        int i = procurarFilho(noFilho, aChave);

                        raiz = new No();

                        noFilho.addChave(aChave);
                        noFilho.ordenarNo();

                        divideNo(raiz, noFilho, i);
                    }else 
                        insereNoNaoCheio(raiz, aChave);
                }
            }
        }
        return true;
    }
    
    public void insereNoNaoCheio(No aNo, int aChave) {
        if (aNo.folha()) {
            aNo.addChave(aChave);
            aNo.ordenarNo();
        }else {
            int i = this.procurarFilho(aNo, aChave);
            
            if (aNo.getFilho(i).numChaves() < iNumMaxChaves)
                insereNoNaoCheio(aNo.getFilho(i), aChave);
            else{
                if (aNo.getFilho(i).numChaves() == iNumMaxChaves) {
                    if (aNo.getFilho(i).folha()) {
                        aNo.getFilho(i).addChave(aChave);
                        aNo.getFilho(i).ordenarNo();

                        divideNo(aNo, aNo.getFilho(i), i);
                    } else 
                        insereNoNaoCheio(aNo.getFilho(i), aChave);
                }
            }
        }
    }
    
    public void divideNo(No aPai, No aFilho, int aIndice) {
        aPai.addChave(aFilho.getChave(iNumMinChaves));
        aPai.ordenarNo();
        aFilho.removeChavePeloIndice(iNumMinChaves);

        No noNovo = new No();
        
        while (aFilho.numChaves() > iNumMinChaves) {
            noNovo.addChave(aFilho.getChave(iNumMinChaves));
            aFilho.removeChavePeloIndice(iNumMinChaves);
        }
        
        if (!aFilho.folha()) {
            while (aFilho.numFilhos() > (iNumMinChaves + 1)) {
                noNovo.addFilho(aFilho.getFilho(iNumMinChaves + 1));
                aFilho.removeFilho(iNumMinChaves + 1);
            }
        }
        
        if (aPai.folha()) {
            aPai.addFilho(aFilho);
            aPai.addFilhosNoIndice(aPai.numFilhos(), noNovo);
        } else
            aPai.addFilhosNoIndice(aIndice + 1, noNovo);
        
        if (aPai.numChaves() > iNumMaxChaves) {
            int iMaiorChave = aPai.maiorChave();
            No noPai = buscaPai(raiz, iMaiorChave);
            
            if (noPai == null) {
                No novaRaiz = new No();
                raiz = novaRaiz;
                
                divideNo(novaRaiz, aPai, aIndice);
            } else {
                aIndice = this.procurarFilho(noPai, iMaiorChave);
                divideNo(noPai, aPai, aIndice);
            }
        }
    }
    
    public boolean remove(int aChave) {
        if (this.buscaChave(raiz, aChave) == null)
            return false;
        else {
            No noRemove = this.buscaChave(raiz, aChave);
            
            if (noRemove.folha()) {
                noRemove.removeChavePeloValor(aChave);
                if (noRemove != raiz)
                    balanceia_folha(noRemove);
            }else {
                if (!noRemove.folha()) {
                    No noAntecessor = buscaAntecessor(aChave);
                    
                    int iMaiorChaveAntecessor = noAntecessor.maiorChave();
                    System.out.println("O anterior é >>  " + iMaiorChaveAntecessor);
                    noAntecessor.removeChavePeloValor(iMaiorChaveAntecessor);

                    noRemove.removeChavePeloValor(aChave);
                    noRemove.addChave(iMaiorChaveAntecessor);
                    noRemove.ordenarNo();

                    balanceia_folha(noAntecessor);
                }
            }
        }
        return true;
    }
    
    void balanceia_folha(No aNo) {
        System.out.println("Balancenando árvore");
        if (aNo.numChaves() < iNumMinChaves) {
            No noPai = this.buscaPaiDoNo(aNo, raiz);
            
            int j = 0;
            while (noPai.getFilho(j) != aNo) {
                j++;
            }
            if (j == 0 || noPai.getFilho(j - 1).numChaves() == iNumMinChaves) {
                if ((j == noPai.numChaves()) || noPai.getFilho(j + 1).numChaves() == iNumMinChaves)
                    diminuiAltura(aNo);
                else
                    balanceia_dir_esq(noPai, j, aNo, noPai.getFilho(j + 1));
            } else
                balanceia_dir_esq(noPai, j - 1, noPai.getFilho(j - 1), aNo);
        }
    }
    
    void balanceia_dir_esq(No aPai, int iIndice, No aEsq, No aDir) {
        if (!aDir.folha()) {
            for (int i = aDir.numFilhos(); i > 1; i--)
                aDir.addFilhosNoIndice(i + 1, aDir.getFilho(i));
        }

        if (aDir.numChaves() > iNumMinChaves && aEsq.numChaves() < iNumMinChaves) {
            aEsq.addChave(aPai.getChave(iIndice));
            aPai.removeChavePeloIndice(iIndice);

            aPai.addChaveNoIndice(iIndice, aDir.menorChave());
            aDir.removeChavePeloValor(aDir.menorChave());

            aEsq.ordenarNo();
            aDir.ordenarNo();
            aPai.ordenarNo();
        } else {
            if (aEsq.numChaves() > iNumMinChaves && aDir.numChaves() < iNumMinChaves) {
                aDir.addChave(aPai.getChave(iIndice));
                aPai.removeChavePeloIndice(iIndice);

                aPai.addChave(aEsq.maiorChave());
                aEsq.removeChavePeloValor(aEsq.maiorChave());

                aEsq.ordenarNo();
                aDir.ordenarNo();
                aPai.ordenarNo();

            } else {
                aDir.addChaveNoIndice(0, aPai.getChave(iIndice));
                aPai.removeChavePeloIndice(iIndice);

                aPai.addChaveNoIndice(iIndice, aEsq.maiorChave());
                aEsq.removeChavePeloValor(aEsq.maiorChave());
            }
        }

        if (!aEsq.folha()) {
            aDir.addFilhosNoIndice(0, aEsq.getFilho(aEsq.numFilhos() - 1));
            aEsq.removeFilho(aEsq.numFilhos() - 1);
        }
    }
    
    void diminuiAltura(No aNo) {
        if (aNo == raiz) {
            exibirNo(aNo);
            if (aNo.numChaves() == 0)
                raiz = aNo.getFilho(0);
        } else {
            No noPai = this.buscaPaiDoNo(aNo, raiz);
            if (aNo.numChaves() < iNumMinChaves) {
                int j = 0;
                while (noPai.getFilho(j) != aNo) {
                    j++;
                }
                if (j > 0)
                    juncaoNo(noPai, j - 1);
                else
                    juncaoNo(noPai, j);
                diminuiAltura(noPai);
            }
        }
    }
    
    void juncaoNo(No aNo, int iIndice) {
        No noEsq = aNo.getFilho(iIndice);
        No noDir = aNo.getFilho(iIndice + 1);

        noEsq.addChave(aNo.getChave(iIndice));
        aNo.removeChavePeloIndice(iIndice);

        while (noDir.numChaves() > 0) {
            noEsq.addChave(noDir.getChave(0));
            noDir.removeChavePeloIndice(0);
        }
        noEsq.ordenarNo();

        if (!noDir.folha()) {
            while (noDir.numFilhos() > 0) {
                noEsq.addFilho(noDir.getFilho(0));
                noDir.removeFilho(0);
            }
        }
        aNo.removeFilho(iIndice + 1);
    }
    
    public No buscaChave(No aNo, int aChave) {
        int i = 0;
        int iNumChaves = aNo.numChaves();
        
        while (i < iNumChaves && aChave > aNo.getChave(i)) {
            i++;
        }
        
        if (i < iNumChaves && aChave == aNo.getChave(i))
            return aNo;

        if (aNo.getListFilhos().isEmpty())
            return null;
        else
            aNo = buscaChave(aNo.getListFilhos().get(i), aChave);
        return aNo;
    }
    
    public No buscaPai(No aNo, int aChave) {
        int i = this.procurarFilho(aNo, aChave);
        
        if (aNo.folha())
            return null;

        if (aNo.getFilho(i).contemChave(aChave))
            return aNo;
        else
            aNo = buscaPai(aNo.getFilho(i), aChave);
        return aNo;
    }
    
    public No buscaPaiDoNo(No aFilho, No aPai) {
        No aux;
        if (aFilho == raiz)
            return null;
        else {
            for (int i = 0; i < aPai.numFilhos(); i++) {
                if (aPai.getFilho(i) == aFilho)
                    return aPai;
                else if (aPai.getFilho(i) == null)
                    return null;
            }
            for (int i = 0; i < aPai.numFilhos(); i++) {
                aux = buscaPaiDoNo(aFilho, aPai.getFilho(i));
                if (aux != null)
                    return aux;
            }
        }
        return null;
    }
    
    No buscaAntecessor(int aChave) {
        int iChave = aChave - 1;
        while (this.buscaChave(raiz, iChave) == null) {
            iChave--;
        }
        return this.buscaChave(raiz, iChave);
    }
    
    public void exibir(No aNo) {
        if (aNo.folha())
            exibirNo(aNo);
        else {
            int i = 0;
            int k = 0;

            while (i < aNo.numFilhos()) {
                if (k < aNo.numChaves()) {
                    System.out.println("=======================");
                    System.out.print("Nó Pai >> " + aNo.getChave(k));
                    System.out.print("\nNumero de filhos >> " + aNo.numFilhos());
                    System.out.println("\n=======================");
                    k++;
                }
                exibir(aNo.getFilho(i));
                i++;
            }
        }
    }
    
    public void exibirNo(No aNo) {
        System.out.println("=======================");
        if (aNo != null && aNo.numChaves() > 0) {
            for (int i = 0; i < aNo.numChaves(); i++) {
                System.out.print(aNo.getChave(i) + ", ");
            }
        } else 
            System.out.println("O nó está vazio!");
        System.out.println("\n=======================");
    }
    
    public No getRaiz() {
        return raiz;
    }
    
    public int procurarFilho(No aNo, int aChave) {
        int i = 0;
        int iTamanho = aNo.getListChaves().size();
        while (i < iTamanho && aChave > aNo.getChave(i)) {
            i++;
        }
        return i;
    }
    
    public void setMaximoChaves(int x) {
        iNumMaxChaves = x;
    }
    
    public int getMaximoChaves() {
        return iNumMaxChaves;
    }
    
    public int getMinimoChaves() {
        return iNumMinChaves;
    }
    
    public void trocaAntecessor(No node, int aChave) {
        No node_aux = node.getAntecessor(aChave);
        int K;

        K = node_aux.getListChaves().get(node_aux.getListChaves().size() - 1);

        node_aux.removeChavePeloIndice(node_aux.getIndexChave(K));
        node_aux.addChave(aChave);

        node.removeChavePeloIndice(node.getIndexChave(aChave));
        node.addChave(K);
    }
    
    public void trocaSucessor(No node, int aChave) {
        No node_aux = node.getSucessor(aChave);
        int K;
        K = node_aux.getListChaves().get(0);

        node.removeChavePeloIndice(node.getIndexChave(aChave));
        node.addChave(K);
    }
    
    public No getPai(No node, No raiz) {
        int i;

        for (i = 0; i < raiz.getListChaves().size(); i++) {
            if (raiz.getListFilhos().get(i) == node)
                return raiz;
            else
                return (this.getPai(node, raiz.getListFilhos().get(i)));
        }
        return null;
    }
    
    public No irmaoEsquerdo(No node) {
        No pai_aux;
        pai_aux = this.getPai(node, this.getRaiz());

        if (this.getIndexFilho(node) == 0)
            return null;
        else 
            return pai_aux.getListFilhos().get(this.getIndexFilho(node) - 1);
    }
    
    public No irmaoDireito(No node) {
        No pai_aux;
        pai_aux = this.getPai(node, this.getRaiz());

        if (this.getIndexFilho(node) == pai_aux.getListFilhos().size())
            return null;
        else {
            if (pai_aux.getListFilhos().get(this.getIndexFilho(node) + 1) != null)
                return pai_aux.getListFilhos().get(this.getIndexFilho(node) + 1);
            else
                return null;
        }
    }
     
    public int getIndexFilho(No node) {
        int i;
        No no_pai = this.getPai(node, raiz);

        for (i = 0; i < no_pai.getListFilhos().size(); i++) {
            if (no_pai.getListFilhos().get(i) == node)
                return i;
        }
        return -1;
    }
    
    public int getAlturaArvore(No n, int cont) {
        if (n.folha())
            return cont;
        else
            return getAlturaArvore(n.filhoEsquerdo(n.getChave(0)), cont + 1);
    }
    
}

