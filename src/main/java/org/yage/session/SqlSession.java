package org.yage.session;

public interface SqlSession {

    // 获取配置
    Configuration getConfiguration();

    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object... parameter);

    <T> T getMapper(Class<T> type);
}
