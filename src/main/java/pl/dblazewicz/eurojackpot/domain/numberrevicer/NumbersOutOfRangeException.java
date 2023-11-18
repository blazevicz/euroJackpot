package pl.dblazewicz.eurojackpot.domain.numberrevicer;

import java.util.Set;

class NumbersOutOfRangeException extends Exception {

    public NumbersOutOfRangeException(String msg, Set<Integer> mainNumbersFromUser, Set<Integer> bonusNumbersFromUser) {
    }
}
