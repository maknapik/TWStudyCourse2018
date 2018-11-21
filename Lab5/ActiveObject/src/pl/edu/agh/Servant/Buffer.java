package pl.edu.agh.Servant;

import pl.edu.agh.Parameters;

import java.util.ArrayList;
import java.util.List;


public class Buffer {
    private List<Integer> elements;

    public Buffer() {
        elements = new ArrayList<>(Parameters.CAPACITY);
    }

    public void put(List<Integer> elementsToAdd) {
        if(elements.size() + elementsToAdd.size() > Parameters.CAPACITY) {
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
        return Parameters.CAPACITY;
    }
}
