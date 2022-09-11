package org.yage.binding;


import org.yage.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 映射器 InvocationHandler
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 2438484912041033251L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 不代理Object 方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        return sqlSession.selectOne(method.getName(), args);
    }
}
