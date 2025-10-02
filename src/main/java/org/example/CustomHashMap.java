package org.example;

import java.util.Objects;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] tableElements;
    private int size;

    public CustomHashMap(){
        tableElements = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    private static class Node<K, V>{
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int hash(K key) {
        int h;
        //return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        return (key == null) ? 0 : (h = Objects.hash(key)) ^ (h >>> 16);
    }

    private void resize(){
        Node<K, V>[] oldTableElements = tableElements;
        int length = (oldTableElements == null) ? 0 : oldTableElements.length;
        int newSize = 0;
        if(length > DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR) {
            newSize = length * 2;
        }
        else if(length == 0) {
            newSize = DEFAULT_INITIAL_CAPACITY;
        }

        Node<K, V>[] newTableElements = new Node[newSize];

        for(int i = 0; i < length; i++){
            Node<K, V> node = tableElements[i];
            while (node != null){
                Node<K, V> nextNode = node.next;
                int nodeIndex = calculateNodeIndex(newTableElements, node.hash);
                node.next = newTableElements[nodeIndex];
                newTableElements[nodeIndex] = node;
                node = nextNode;
            }
        }

        tableElements = newTableElements;

    }

    public V put(K key, V value){
        int nodeHash = hash(key);
        int nodeIndex = calculateNodeIndex(tableElements, nodeHash);

        for(Node<K, V> node = tableElements[nodeIndex]; node != null; node = node.next){
            if(node.hash == nodeHash && (key == node.key || (key != null && key.equals(node.key)))){
                V existValue = node.value;
                node.value = value;
                return existValue;
            }
        }

        tableElements[nodeIndex] = new Node<>(nodeHash, key, value, tableElements[nodeIndex]);
        size++;
        if(size > tableElements.length * DEFAULT_LOAD_FACTOR){
            resize();
        }

        return null;
    }

    public V remove(K key){
        int nodeHash = hash(key);
        int nodeIndex = calculateNodeIndex(tableElements, nodeHash);

        Node<K, V> prev = null;
        Node<K, V> currentNode = tableElements[nodeIndex];

        while (currentNode != null){
            if(currentNode.hash == nodeHash && (key == currentNode.key || (key != null && key.equals(currentNode.key)))){
                if(prev == null) {
                    tableElements[nodeIndex] = currentNode.next;
                }
                else {
                    prev.next = currentNode.next;
                }
                size--;
                return currentNode.value;
            }
            prev = currentNode;
            currentNode = currentNode.next;
        }

        return null;
    }

    public V get(K key) {
        int nodeHash = hash(key);
        int nodeIndex = calculateNodeIndex(tableElements, nodeHash);

        for (Node<K, V> node = tableElements[nodeIndex]; node != null; node = node.next) {
            if (node.hash == nodeHash && (key == node.key || (key != null && key.equals(node.key)))) {
                return node.value;
            }
        }
        return null;
    }

    public int getSize(){
        return this.size;
    }

    public int calculateNodeIndex(Node<K, V>[] tableElements, int nodeHash){
        return (tableElements.length - 1) & nodeHash;
    }

}
