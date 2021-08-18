package Util;

class Nodo<Elemento> {
    
    private Elemento elemento;
    private Nodo<Elemento> proximo;

    public Nodo(Elemento elemento, Nodo<Elemento> proximo){
        this.elemento = elemento;
        this.proximo = proximo;
    }
    
    public Elemento getElemento(){
        return this.elemento;
    }

    public void setElemento(Elemento elemento){
        this.elemento = elemento;
    }

    public Nodo getProximo(){
        return this.proximo;
    }

    public void setProximo(Nodo<Elemento> proximo){
        this.proximo = proximo;
    }
}
