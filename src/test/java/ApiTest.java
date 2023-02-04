import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.yage.builder.xml.XMLConfigBuilder;
import org.yage.dao.IUserDao;
import org.yage.io.Resources;
import org.yage.po.User;
import org.yage.session.Configuration;
import org.yage.session.SqlSession;
import org.yage.session.SqlSessionFactory;
import org.yage.session.SqlSessionFactoryBuilder;
import org.yage.session.defaults.DefaultSqlSession;

import java.io.IOException;
import java.io.Reader;

@Slf4j
class ApiTest {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        log.info("测试结果：{}", user);
    }


    @Test
    public void test_selectOne() throws IOException {
        // 解析 XML
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xmlConfigBuilder.parse();

        // 获取 DefaultSqlSession
        SqlSession sqlSession = new DefaultSqlSession(configuration);

        // 执行查询：默认是一个集合参数
        Object[] req = {1L};
        Object res = sqlSession.selectOne("org.yage.dao.IUserDao.queryUserInfoById", req);
        log.info("测试结果：{}", OBJECT_MAPPER.writeValueAsString(res));
    }

}
