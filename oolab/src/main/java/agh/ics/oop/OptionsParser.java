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
            switch (arg){
                case "f" -> moveDirectionList.add(MoveDirection.FORWARD);
                case "b" -> moveDirectionList.add(MoveDirection.BACKWARDS);
                case "l" -> moveDirectionList.add(MoveDirection.LEFT);
                case "r" -> moveDirectionList.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            };
        }

        return moveDirectionList;
    }
}
