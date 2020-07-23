package co.edu.cedesistemas.leveling.functional;

import java.util.List;

@FunctionalInterface
public interface SortFunction<T extends Comparable<T>> {
    void sort(List<T> list);
}
