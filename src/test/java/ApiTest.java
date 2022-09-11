import dao.IUserDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yage.binding.MapperRegister;
import org.yage.session.SqlSession;
import org.yage.session.SqlSessionFactory;
import org.yage.session.defaults.DefaultSqlSessionFactory;

public class ApiTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_mapperProxyFactory() {

        MapperRegister mapperRegister = new MapperRegister();
        mapperRegister.addMappers("dao");

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        String aa = mapper.queryUserName("aa");
        logger.info("result: {}", aa);
    }
}
