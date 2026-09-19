package estructuras;

import java.util.Comparator;

public class ColaPrioridad<T> implements Cola<T> {
    private NodoPrioridad<T> frente;
    private int tamanio;
    private final Comparator<? super T> comparador;

    private static class NodoPrioridad<E> {
        E dato;
        NodoPrioridad<E> siguiente;

        NodoPrioridad(E dato) {
            this.dato = dato;
        }
    }

    public ColaPrioridad(Comparator<? super T> comparador) {
        this.frente = null;
        this.tamanio = 0;
        this.comparador = comparador;
    }

    @Override
    public void enqueue(T dato) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato);
        if (estaVacia() || comparador.compare(dato, frente.dato) < 0) {
            nuevo.siguiente = frente;
            frente = nuevo;
        } else {
            NodoPrioridad<T> actual = frente;
            while (actual.siguiente != null && comparador.compare(dato, actual.siguiente.dato) >= 0) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public T dequeue() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola está vacía");
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        tamanio--;
        return dato;
    }

    @Override
    public T peek() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola está vacía");
        }
        return frente.dato;
    }

    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray(T[] a) {
        if (a.length < tamanio) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), tamanio);
        }
        NodoPrioridad<T> actual = frente;
        int i = 0;
        while (actual != null) {
            a[i++] = actual.dato;
            actual = actual.siguiente;
        }
        if (a.length > tamanio) {
            a[tamanio] = null;
        }
        return a;
    }
}
