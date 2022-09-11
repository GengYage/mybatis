package org.yage.session.defaults;

import org.yage.binding.MapperRegister;
import org.yage.session.SqlSession;
import org.yage.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final MapperRegister mapperRegister;

    public DefaultSqlSessionFactory(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegister);
    }
}
