package org.yage.binding;


import org.yage.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 映射器 InvocationHandler
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 2438484912041033251L;

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 不代理Object 方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        MapperMethod mapperMethod = cachedMapperMethod(method);

        return mapperMethod.execute(sqlSession, args);
    }

    // 从缓存中查找
    private MapperMethod cachedMapperMethod(Method method) {
        return methodCache.computeIfAbsent(method,
                key -> new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
    }
}
