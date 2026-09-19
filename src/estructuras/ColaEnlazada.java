package estructuras;

public class ColaEnlazada<T> implements Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public ColaEnlazada() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    @Override
    public void enqueue(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tamanio++;
    }

    @Override
    public T dequeue() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola está vacía");
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
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
        Nodo<T> actual = frente;
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
