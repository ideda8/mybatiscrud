package com.da.test;

import com.da.dao.AccountDao;
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

public class AccountTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void bef() throws Exception{
        //获得主配置文件流信息
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        //获取SqlSessionFaction工厂对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void findAccountWithUser() throws Exception{

        //通过获取SqlSessionFaction工厂对象工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //使用SqlSession对象来生成接口代理对象
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);

        //调用代理对象中的方法
        List<Account> list = accountDao.findAccountWithUser();

        for (Account acc : list ) {
            System.out.println("["+acc.getMoney()+"]");
            User u = acc.getUser();
            System.out.println("("+u.getUsername()+")");
        }

        sqlSession.close();

    }



}
