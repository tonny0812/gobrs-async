package com.gobrs.async.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The type Gobrs spring.
 *
 * @author sizegang1
 * @date 2022 -01-27 23:56 2022-01-27 23:56
 */
public class GobrsSpring implements ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * The Logger.
     */
    static Logger logger = LoggerFactory.getLogger(GobrsSpring.class);
    /**
     * The constant applicationContext.
     */
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (GobrsSpring.applicationContext == null) {
            GobrsSpring.applicationContext = applicationContext;
        }
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     */
    public static Object getBean(String name) {


        try {
            return getApplicationContext() == null ? cf.getBean(name) : getApplicationContext().getBean(name);
        } catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
            if (getApplicationContext() == null) {
                return cf.getBean(lowerFirstChar(name));
            }
            return getApplicationContext().getBean(lowerFirstChar(name));
        } catch (Exception ex) {
            logger.error("Gobrs-Spring genBean error{}", ex);
        }
        return null;
    }

    private static String lowerFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
//通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return cf.getBean(clazz);
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * Gets bean.
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
//通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    private static ConfigurableListableBeanFactory cf;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        cf = beanFactory;
    }
}
