package tech.inno.tretyakov;

import java.lang.reflect.Proxy;

public final class Utils {

    private Utils() {
    }

    public static <T> T cache(Object object) {
        Class<?> objectClass = object.getClass();
        if (object instanceof Fractionable) {
            return (T) Proxy.newProxyInstance(objectClass.getClassLoader(),
                    new Class[]{Fractionable.class},
                    new CacheInvokeHandler(object));
        } else {
            return (T) object;
        }
    }
}