package org.yage.session.defaults;

import org.yage.binding.MapperRegister;
import org.yage.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    /**
     * 映射注册机
     */
    private MapperRegister mapperRegister;

    public DefaultSqlSession(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了! " + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你被代理了! " + "方法:" + statement + "入参:" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegister.getMapper(type, this);
    }
}
