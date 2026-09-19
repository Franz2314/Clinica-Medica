package estructuras;

/**
 * Nodo para la estructura de lista doblemente enlazada.
 * Guarda la informacion del nodo y las referencias al anterior y al siguiente.
 */
public class NodoDoble<T> {

    public T dato;
    public NodoDoble<T> anterior;
    public NodoDoble<T> siguiente;

    public NodoDoble(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
}
