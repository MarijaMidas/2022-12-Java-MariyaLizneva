package app.cache;

import java.util.*;

public class MyCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();

    private final List<Listener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(Listener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
    }
}
