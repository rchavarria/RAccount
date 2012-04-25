package es.rchavarria.raccount.frontend.dataMixer;

public interface FieldMixer<T> {

    /**
     * Copia parcialmente campos de {@code origin} a {@code target}
     * 
     * @param target
     *            Elemento que retornará con algunos campos copiados de {@code origin}
     * @param origin
     *            Elemento del que copiar ciertos campos
     * @return El argumento {@code target} modificado
     */
    T mix(T target, T origin);
}
