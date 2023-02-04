package org.yage.session.defaults;

import lombok.extern.slf4j.Slf4j;
import org.yage.mapping.BoundSql;
import org.yage.mapping.Environment;
import org.yage.mapping.MappedStatement;
import org.yage.session.Configuration;
import org.yage.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
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
    public <T> T selectOne(String statement, Object... parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            Environment environment = configuration.getEnvironment();

            Connection connection = environment.getDataSource().getConnection();

            BoundSql boundSql = mappedStatement.getBoundSql();
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            preparedStatement.setLong(1, Long.parseLong(parameter[0].toString()));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
            return objList.get(0);
        } catch (Exception e) {
            log.error("select one form database error", e);
            return null;
        }

    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }


    @SuppressWarnings("unchecked")
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

                    log.info("method:{}, columnName:{}, value:{}", setMethod, columnName, value);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            log.error("parse result error", e);
        }
        return list;
    }
}
