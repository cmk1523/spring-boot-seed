package com.techdevsolutions.service;

import com.techdevsolutions.dao.mysql.InstallerMySqlDao;
import com.techdevsolutions.dao.mysql.user.UserMySqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallerService {
    InstallerMySqlDao installerDao;

    @Autowired
    public InstallerService(InstallerMySqlDao installerDao) {
        this.installerDao = installerDao;
    }

    public void install() {
        this.installerDao.createTables();
    }
}
