package com.hjg.dao;

import com.hjg.base.MapperMarker;
import com.hjg.model.User;
import com.hjg.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends MapperMarker {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_users
     *
     * @mbggenerated Tue Jul 01 11:30:26 CST 2014
     */
    int updateByPrimaryKey(User record);
}