package com.varun.Model;

import java.util.logging.Logger;

import com.varun.Controller.ChatList;
import com.varun.Logger.LoggerUtil;
import com.varun.Orm.Column;
import com.varun.Orm.Table;

@Table(name="group_members")
public class GroupMembersModel {
	
	private static final Logger logger=LoggerUtil.getLogger(ChatList.class);

	@Column
    Integer member_id=null;
	
	@Column
 	Integer group_id=null;

    public Integer getMember_id() {
		return member_id;
	}
	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
}
