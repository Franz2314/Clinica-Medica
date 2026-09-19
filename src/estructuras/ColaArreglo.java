package estructuras;

public class ColaArreglo<T> implements Cola<T> {
    private T[] elementos;
    private int frente;
    private int fin;
    private int tamanio;
    private static final int CAPACIDAD_INICIAL = 50;

    @SuppressWarnings("unchecked")
    public ColaArreglo() {
        elementos = (T[]) new Object[CAPACIDAD_INICIAL];
        frente = 0;
        fin = -1;
        tamanio = 0;
    }

    @Override
    public void enqueue(T dato) {
        if (tamanio == elementos.length) {
            duplicarCapacidad();
        }
        fin = (fin + 1) % elementos.length;
        elementos[fin] = dato;
        tamanio++;
    }

    @Override
    public T dequeue() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola está vacía");
        }
        T dato = elementos[frente];
        elementos[frente] = null; // Evitar fugas de memoria
        frente = (frente + 1) % elementos.length;
        tamanio--;
        return dato;
    }

    @Override
    public T peek() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola está vacía");
        }
        return elementos[frente];
    }

    @Override
    public boolean estaVacia() {
        return tamanio == 0;
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
        for (int i = 0; i < tamanio; i++) {
            a[i] = elementos[(frente + i) % elementos.length];
        }
        if (a.length > tamanio) {
            a[tamanio] = null;
        }
        return a;
    }

    @SuppressWarnings("unchecked")
    private void duplicarCapacidad() {
        T[] nuevoArreglo = (T[]) new Object[elementos.length * 2];
        for (int i = 0; i < tamanio; i++) {
            nuevoArreglo[i] = elementos[(frente + i) % elementos.length];
        }
        elementos = nuevoArreglo;
        frente = 0;
        fin = tamanio - 1;
    }
}
