package com.da.dao;

import com.da.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserDao {

    @Select("select * from USER")
    public List<User> findAll();


    @Insert("insert into USER(username, birthday, sex, address) " +
            "values " +
            "(#{username}, #{birthday}, #{sex}, #{address})")
    public int insert(User user);

    @Delete("delete from USER where id = #{id}")
    public int delete(int id);


    /**
     * 根据id查询用户 可提供给根据Account查询用户的一对一查询使用
     */
    @Select("select * from USER where id = #{id}")
    public User findById(Integer id);

    /**
     * 查询所有用户及用户下所有账户的集合
     */
    @Select("select * from USER")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            @Result(property = "accounts", column = "id", javaType = List.class,   //使用id查询获得Accounts
                    many = @Many(select = "com.da.dao.AccountDao.findAccountByUid", //一对多 调用查询的方法
                            fetchType = FetchType.LAZY))                          //获取方式默认(EAGER) EAGER立即加载 LAZY延时加载
    })
    public List<User> findAllUsrWithAccounts();
}
