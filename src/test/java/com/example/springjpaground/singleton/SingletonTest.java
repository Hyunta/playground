package com.example.springjpaground.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class SingletonTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Setting setting;

    @Autowired
    DefaultListableBeanFactory beanFactory;

    @Test
    @DisplayName("같은 타입의 빈은 Configuration으로 등록되면 같은 인스턴스를 반환한다.")
    void same_bean() {
        Setting setting1 = applicationContext.getBean("setting1", Setting.class);
        System.out.println("setting1 = " + setting1);

//        Setting setting2 = applicationContext.getBean("setting2", Setting.class);
//        System.out.println("setting2 = " + setting2);

        System.out.println("setting = " + setting);

        Setting bean = applicationContext.getBean(Setting.class);

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }

        Assertions.assertThat(bean).isEqualTo(setting1);
        Assertions.assertThat(setting).isEqualTo(setting1);
    }

    @Configuration
    static class SameBeanConfig {

        @Bean
        public Setting setting1() {
            return new Setting();
        }

//        @Bean
//        public Setting setting2() {
//            return new Setting();
//        }
    }
}
