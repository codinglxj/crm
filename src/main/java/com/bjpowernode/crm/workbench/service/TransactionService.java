package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

/**
 * @authot : lxj
 * @Date : 2021/3/29 10:22
 */
public interface TransactionService {
    List<User> getUserList();

    boolean doSave(Tran tran, String customerName);
}
