package com.hongchao.enkore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongchao.enkore.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>
{
}
