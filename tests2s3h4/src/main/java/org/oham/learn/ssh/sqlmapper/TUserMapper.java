package org.oham.learn.ssh.sqlmapper;

import java.util.List;

import org.oham.learn.ssh.beans.TUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserMapper {

	public List<TUser> selectTUserByExample(TUser user);
}
