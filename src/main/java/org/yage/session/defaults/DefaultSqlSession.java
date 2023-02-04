package org.yage.session.defaults;

import org.yage.mapping.MappedStatement;
import org.yage.session.Configuration;
import org.yage.session.SqlSession;

import java.util.Arrays;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了! " + statement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String statement, Object... parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("方法:" + statement + "入参:" + Arrays.toString(parameter) + "\n待执行SQL:" + mappedStatement.getSql());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }
}
