package org.yage.session.defaults;

import org.yage.binding.MapperRegister;
import org.yage.session.SqlSession;

import java.util.Arrays;

public class DefaultSqlSession implements SqlSession {

    private final MapperRegister mapperRegister;

    public DefaultSqlSession(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了! " + statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object... parameter) {
        return (T) ("你被代理了! " + "方法:" + statement + "入参:" + Arrays.toString(parameter));
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegister.getMapper(type, this);
    }
}
