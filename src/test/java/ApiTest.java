import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.yage.dao.IUserDao;
import org.yage.io.Resources;
import org.yage.session.SqlSession;
import org.yage.session.SqlSessionFactory;
import org.yage.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

@Slf4j
class ApiTest {
    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        String res = userDao.queryUserInfoById("10001");
        log.info("测试结果：{}", res);
    }
}
