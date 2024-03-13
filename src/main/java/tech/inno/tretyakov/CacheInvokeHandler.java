package tech.inno.tretyakov;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheInvokeHandler implements InvocationHandler {
    private final Object object;
    // Закешированные значения будем хранить в коллекции Map. В качестве ключа используется имя кешируемого поля/результата (задается аннотацией)
    private final Map<Fields, Double> cache = new HashMap<>();

    CacheInvokeHandler(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Вызов перехвачен Proxy");
        Method invokeMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());
        Fields fieldName;

        if (invokeMethod.isAnnotationPresent(Cache.class)) {
            fieldName = invokeMethod.getDeclaredAnnotation(Cache.class).fieldName();
            if (fieldName !=null) {
                if (this.cache.containsKey(fieldName)) {
                    System.out.println("Значение найдено в кеше!");
                    return this.cache.get(fieldName);
                }
            }
            Double result = (Double) invokeMethod.invoke(this.object, args);
            this.cache.put(fieldName, result);
            System.out.println("Рассчитанное значение поместили в кеш");
            return  result;
        }

        if (invokeMethod.isAnnotationPresent(Mutators.class) || invokeMethod.isAnnotationPresent(Mutator.class)) {
            Mutator[] mutatorAnnotatios = invokeMethod.getDeclaredAnnotationsByType(Mutator.class);
            Arrays.asList(mutatorAnnotatios).forEach(it -> this.cache.remove(it.fieldName()));
            System.out.println("Кеш очищен");
        }

        return invokeMethod.invoke(this.object, args);
    }

}
