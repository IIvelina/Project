package com.project.bank.config;

import com.project.bank.service.RoleService;
import com.project.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@DependsOn("entityManagerFactory")
public class DatabaseInit implements CommandLineRunner {

    private final RoleService roleService;
    private final DataSource dataSource;
    private final UserService userService;

    @Autowired
    public DatabaseInit(RoleService roleService, DataSource dataSource, UserService userService) {
        this.roleService = roleService;
        this.dataSource = dataSource;

        this.userService = userService;
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
