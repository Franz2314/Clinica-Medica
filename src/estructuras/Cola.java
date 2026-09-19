package estructuras;

public interface Cola<T> {
    void enqueue(T dato);
    T dequeue();
    T peek();
    boolean estaVacia();
    int tamanio();
    T[] toArray(T[] a);
}
