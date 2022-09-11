package org.yage.binding;

import cn.hutool.core.lang.ClassScanner;
import org.yage.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperRegister {
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        @SuppressWarnings("unchecked") final MapperProxyFactory<T> mapperProxyFactory =
                (MapperProxyFactory<T>) knownMappers.get(type);

        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }

        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e);
        }
    }

    public <T> void addMapper(Class<T> type) {
        // type是接口才会注册
        if (type.isInterface()) {
            if (hashMapper(type)) {
                // 不允许重复添加
                throw new RuntimeException("Type " + type + " is already known to MapperRegister");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    private <T> boolean hashMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }
}
