package com.project.bank.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Може да добавите допълнителна логика при създаване на сесията, ако е необходимо
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Логика при унищожаване на сесията
    }
}
