package org.yage.session;

import org.yage.builder.xml.XMLConfigBuilder;
import org.yage.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * 工厂建造者,处理IO
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}
