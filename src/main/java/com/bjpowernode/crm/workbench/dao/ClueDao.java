package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {


    List<User> getUserList();

    int doSave(Clue clue);

    Clue doDetail(String id);


    Clue getClue(String clueId);

    int delete(String clueId);
}
