package estructuras;

public class PilaEnlazada<T> implements Pila<T> {
    private Nodo<T> cima;
    private int tamanio;

    public PilaEnlazada() {
        cima = null;
        tamanio = 0;
    }

    @Override
    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamanio++;
    }

    @Override
    public T pop() {
        if (estaVacia()) {
            throw new java.util.EmptyStackException();
        }
        T dato = cima.dato;
        cima = cima.siguiente;
        tamanio--;
        return dato;
    }

    @Override
    public T peek() {
        if (estaVacia()) {
            throw new java.util.EmptyStackException();
        }
        return cima.dato;
    }

    @Override
    public boolean estaVacia() {
        return cima == null;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }
}
