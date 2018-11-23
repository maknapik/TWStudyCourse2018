package pl.edu.agh.Servant;

import java.util.ArrayList;
import java.util.List;


public class Buffer {

    private int capacity;
    private List<Integer> elements;

    public Buffer(int capacity) {
        this.capacity = capacity;
        elements = new ArrayList<>(capacity);
    }

    public void put(List<Integer> elementsToAdd) {
        if(elements.size() + elementsToAdd.size() > capacity) {
            System.out.println("There is no enough space in buffer");
        } else {
            elements.addAll(elementsToAdd);
        }
    }

    public List<Integer> take(int amount) {
        if(elements.size() < amount) {
            System.out.println("There is no enough elements in buffer to take");
            return null;
        } else {
            List<Integer> elementsToReturn = new ArrayList<>();

            for(int i = 0 ; i < amount ; i++) {
                elementsToReturn.add(elements.remove(0));
            }
            return elementsToReturn;
        }
    }

    public int size() {
        return elements.size();
    }

    public int capacity() {
        return capacity;
    }
}
