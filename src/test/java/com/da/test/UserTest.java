package com.da.test;

import com.da.dao.UserDao;
import com.da.po.Account;
import com.da.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void bef() throws Exception{
        //获得主配置文件流信息
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        //获取SqlSessionFaction工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void findAll() throws Exception{

        //通过获取SqlSessionFaction工厂对象工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //使用SqlSession对象来生成接口代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        //调用代理对象中的方法
        List<User> list = userDao.findAll();

        sqlSession.close();
        System.out.println(list);
    }


    @Test
    public void insert() throws Exception{
        //通过获取SqlSessionFaction工厂对象工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //使用SqlSession对象来生成接口代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        User u = new User();
        u.setUsername("haha");
        u.setBirthday(new Date());
        u.setSex("1");
        u.setAddress("sz");

        int i = userDao.insert(u);

        System.out.println(i);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() throws Exception{
        //通过获取SqlSessionFaction工厂对象工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //使用SqlSession对象来生成接口代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        int i = userDao.delete(3);

        System.out.println(i);

        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void findAllUserWithAccount() throws Exception{
        //通过获取SqlSessionFaction工厂对象工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //使用SqlSession对象来生成接口代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        List<User> list = userDao.findAllUsrWithAccounts();

        for (User u : list) {
            System.out.println(u.getUsername());
            List<Account> accounts = u.getAccounts();
            if(accounts!=null && accounts.size()>0) {
                for (Account ac : accounts) {
                    System.out.println(u.getUsername()+ "-->" +ac.getMoney());
                }
            }
            System.out.println();
        }
        sqlSession.close();
    }


}
