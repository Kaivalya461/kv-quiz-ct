package in.kvapps.kv_quiz.temp;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

public class SetLimitedDeque<T> {
    private final Set<T> set;
    private final int capacity;

    public SetLimitedDeque(int capacity) {
        this.set = new LinkedHashSet<>(capacity);
        this.capacity = capacity;
    }

    public void add(T element) {
        if (set.contains(element)) {
            return; // Avoid duplicate elements
        }

        if (set.size() >= capacity) {
            Iterator<T> it = set.iterator();
            if (it.hasNext()) {
                it.next();
                it.remove(); // Removes the oldest element
            }
        }

        set.add(element);
    }

    public T remove() {
        Iterator<T> it = set.iterator();
        if (it.hasNext()) {
            T oldest = it.next();
            it.remove();
            return oldest;
        }
        return null; // Return null if empty
    }

    public void display() {
        System.out.println(set);
    }

    public Set<T> getCopyOfAllElements() {
        return new LinkedHashSet<>(set);
    }
}

