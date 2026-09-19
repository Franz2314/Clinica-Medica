package estructuras;

public interface Pila<T> {
    void push(T dato);
    T pop();
    T peek();
    boolean estaVacia();
    int tamanio();
}
