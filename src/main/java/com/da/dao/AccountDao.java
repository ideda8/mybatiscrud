package com.da.dao;

import com.da.po.Account;
import com.da.po.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface AccountDao {

    /**
     * 查询所有账户及所属用户信息
     */
    @Select("select * from Account")
    @Results({
            @Result(id = true, property = "id", column = "id"),                 //id = true 主键为id
            @Result(property = "uid", column = "uid"),                          //相当于account的user外键
            @Result(property = "money", column = "money"),
            @Result(property = "user", column = "uid", javaType = User.class,   //使用uid查询获得user
                    one = @One(select = "com.da.dao.UserDao.findById",          //一对一 调用查询的方法
                    fetchType = FetchType.LAZY))                                //获取方式默认(EAGER) EAGER立即加载 LAZY延时加载
    })
    public List<Account> findAccountWithUser();

    /**
     * 根据用户id查询账户集合
     */
    @Select("select * from Account where uid = #{uid}")
    public List<Account> findAccountByUid();
}
