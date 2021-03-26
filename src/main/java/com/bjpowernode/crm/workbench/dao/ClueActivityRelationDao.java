package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unBund(String id);

    int doBund(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> selectActivityList(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
