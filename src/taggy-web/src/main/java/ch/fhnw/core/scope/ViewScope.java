package ch.fhnw.core.scope;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.ImmutableMap;

public class ViewScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);

            return object;
        }
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String arg0, Runnable arg1) {

    }

    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    @Override
    public Object resolveContextualObject(String arg0) {
        return null;
    }
    @Bean
    public static CustomScopeConfigurer viewScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(
                new ImmutableMap.Builder<String, Object>().put("view", new ViewScope()).build());
        return configurer;
    }
}