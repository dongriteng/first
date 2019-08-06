package com.dyf.springbootlearning.mapper;

import com.dyf.springbootlearning.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modefied)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModefied})")
    void insert(User user);
}
