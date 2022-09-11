package org.yage.session;

/**
 * 构建SqlSession的工厂
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
