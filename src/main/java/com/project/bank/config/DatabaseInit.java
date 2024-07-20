package com.project.bank.config;

import com.project.bank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@DependsOn("entityManagerFactory")
public class DatabaseInit implements CommandLineRunner {

    private final RoleService roleService;
    private final DataSource dataSource;

    @Autowired
    public DatabaseInit(RoleService roleService, DataSource dataSource) {
        this.roleService = roleService;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();

        // Run the SQL script after the roles are initialized
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("data.sql"));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
    }
}



//package com.project.bank.config;
//
//import com.project.bank.service.RoleService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//@Component
//@DependsOn("entityManagerFactory")
//public class DatabaseInit implements CommandLineRunner {
//    private final RoleService roleService;
//
//    public DatabaseInit(RoleService roleService) {
//        this.roleService = roleService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        roleService.initRoles();
//    }
//}
//
//
