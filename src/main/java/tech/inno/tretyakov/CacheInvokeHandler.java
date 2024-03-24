package tech.inno.tretyakov;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class CacheInvokeHandler implements InvocationHandler {
    private final Object object;
    // Закэшированные значения будем хранить в коллекции Map. В качестве ключа используется строка из имени метода и времени жизни
    private final Map<String, ConcurrentHashMap<String, Map<Long, Double>>> cache = new ConcurrentHashMap<>();

    CacheInvokeHandler(Object object) {
        this.object = object;

        ScheduledExecutorService cacheCleaner = Executors.newSingleThreadScheduledExecutor(it -> {
            Thread th = new Thread(it);
            th.setDaemon(true);
            return th;
        });

        cacheCleaner.scheduleAtFixedRate(() -> {
            for (Map.Entry<String, ConcurrentHashMap<String, Map<Long, Double>>> cacheEntry : cache.entrySet()) {
                long initLifeTime = Long.parseLong(cacheEntry.getKey().substring(cacheEntry.getKey().lastIndexOf('#') + 1));
                initLifeTime = TimeUnit.NANOSECONDS.convert(initLifeTime, TimeUnit.MILLISECONDS);
                long currentTime;
                long cachedValueLifeTime;

                Iterator<Map.Entry<String, Map<Long, Double>>> cachedValuesIterator = cacheEntry.getValue().entrySet().iterator();
                while (cachedValuesIterator.hasNext()) {
                    Map<Long, Double> pair = cachedValuesIterator.next().getValue();
                    cachedValueLifeTime = pair.entrySet().iterator().next().getKey();

                    currentTime = System.nanoTime();

                    // Если время жизни закэшированного значения истекло, то удаляем
                    if (initLifeTime < (currentTime -  cachedValueLifeTime)) {
                        cachedValuesIterator.remove();
                        System.out.println("@@@@ Поток cacheCleaner: Удалил значение из кэша: " + pair);
                        System.out.println("@@@@ Поток cacheCleaner: Содержимое кэша: " + cache + "\n");
                    }
                }
            }
        }, 1, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method invokeMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());
        String cacheKey = method.getName();

        if (invokeMethod.isAnnotationPresent(Cache.class)) {
            System.out.println("Вызов методода " + cacheKey + " через Proxy");

            ConcurrentHashMap<String, Map<Long, Double>> cachedValues;
            Double result;
            cacheKey = cacheKey + '#' + invokeMethod.getDeclaredAnnotation(Cache.class).lifeTime();

            if (!this.cache.containsKey(cacheKey)) {
                this.cache.put(cacheKey, new ConcurrentHashMap<>());
            }
            cachedValues = this.cache.get(cacheKey);

            if (this.object instanceof Fraction) {
                String stateKey = ((Fraction) (this.object)).getStateKey();
                Map<Long, Double> pair = cachedValues.get(stateKey);
                if (pair !=null) {
                    result = pair.entrySet().iterator().next().getValue();
                    System.out.println("Значение найдено в кэше!");
                } else {
                    result = (Double) invokeMethod.invoke(this.object, args);
                    System.out.println("Значения нет в кэше!");
                }
                // В любом случае добаить значение в кэш. Если ранее значения не было, то добавится, если было и было обращение, то обновится время начала жизни
                cachedValues.put(stateKey, Pair.of(System.nanoTime(), result));
                System.out.println("Поместили в кэш/обновили время начала жизни.");
                System.out.println("Содержимое кэша: " + cache + "\n");
                return result;
            }
        }
        return invokeMethod.invoke(this.object, args);
    }
}
