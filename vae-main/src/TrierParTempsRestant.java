package src;

import java.util.Comparator;

public class TrierParTempsRestant implements Comparator<TempsRestant> {
    
    @Override
    public int compare(TempsRestant o1, TempsRestant o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

}
