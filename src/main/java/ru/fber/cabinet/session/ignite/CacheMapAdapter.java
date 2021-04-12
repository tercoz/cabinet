package ru.fber.cabinet.session.ignite;

import org.apache.ignite.client.ClientCache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CacheMapAdapter<K, V> implements Map<K, V> {

    private final ClientCache<K, V> cache;

    public CacheMapAdapter(final ClientCache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        return cache.get((K) key);
    }

    @Override
    public V put(K key, V value) {
        cache.put(key, value);
        return value;
    }

    @Override
    public V remove(Object key) {
        return cache.getAndRemove((K) key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        cache.putAll(m);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
