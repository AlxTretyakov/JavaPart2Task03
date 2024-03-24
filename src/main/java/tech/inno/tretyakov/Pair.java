package tech.inno.tretyakov;

import java.util.Collections;
import java.util.Map;

class Pair<T, U>
{
    private Pair(){};
    public static <T, U> Map<T, U> of(T first, U second) {
        return Collections.singletonMap(first, second);
    }
}