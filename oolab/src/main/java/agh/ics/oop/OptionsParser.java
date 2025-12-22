package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.model.MoveDirection;

/*
* według mnie lepszy rozwiązaniem jest ArrayList, ponieważ gdy wykonuję ruchy zwierzaków w Simulation
* to iteruję się po wszystkich ruchach, wykonuje i-ty ruch dla (i % animals.size) zwierzaka przez co potrzebuję
* szybki dostęp do indeksu co zapewnia arrayList w O(1), a dla linkedListy jest to złożoność O(n), oczywiście dało
* wyjkonać te zadanie dla linkedListy w normalnej złożoności, ale według mnie kod byłby mniej czytelny i wymagałby dodatkowych
* warunków.
 */

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args){
        List<MoveDirection> moveDirectionList = new ArrayList<>();

        for(String arg : args){
            try {
                MoveDirection direction = MoveDirection.fromString(arg);
                moveDirectionList.add(direction);
            } catch (IllegalStateException e) {
                System.err.println("Zlapano blad [" + arg + "]. " + e.getMessage());
            }
        }

        return moveDirectionList;
    }
}
