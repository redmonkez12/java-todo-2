package com.example.todo2;

import com.example.todo2.entities.RoleEntity;
import com.example.todo2.enums.RoleEnum;
import com.example.todo2.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class Todo2Application {

    public final RoleRepository roleRepository;

    @Autowired
    public Todo2Application(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Todo2Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }


    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            try {
                this.roleRepository.saveAll(List.of(
                        new RoleEntity(RoleEnum.USER.name),
                        new RoleEntity(RoleEnum.ADMIN.name),
                        new RoleEntity(RoleEnum.SUPER_ADMIN.name)
                ));
            } catch (Exception e) {

            }
        };
    }

}
