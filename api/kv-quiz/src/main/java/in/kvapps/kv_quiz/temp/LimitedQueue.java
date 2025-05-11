package in.kvapps.kv_quiz.temp;

import java.util.ArrayDeque;
import java.util.Deque;

public class LimitedQueue<T> {
    private final Deque<T> deque;
    private final int capacity;

    public LimitedQueue(int capacity) {
        this.deque = new ArrayDeque<>(capacity);
        this.capacity = capacity;
    }

    public void add(T element) {
        if (deque.size() >= capacity) {
            deque.pollFirst(); // Removes the oldest element
        }
        deque.addLast(element);
    }

    public T remove() {
        return deque.pollFirst(); // Removes and returns the oldest element
    }

    public void display() {
        System.out.println(deque);
    }
}
