import dao.IUserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.yage.binding.MapperRegister;
import org.yage.session.SqlSession;
import org.yage.session.SqlSessionFactory;
import org.yage.session.defaults.DefaultSqlSessionFactory;

@Slf4j
class ApiTest {

    @Test
    public void test_mapperProxyFactory() {

        MapperRegister mapperRegister = new MapperRegister();
        mapperRegister.addMappers("dao");

        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        String aa = mapper.queryUserName("aa");
        log.info("result: {}", aa);
    }
}
