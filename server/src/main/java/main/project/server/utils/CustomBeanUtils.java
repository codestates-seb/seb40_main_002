package main.project.server.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
public class CustomBeanUtils<T> {
    public T copyNonNullProperties(T source, T destination) {
        if (source == null || destination == null || source.getClass() != destination.getClass()) {
            return null;
        }

        final BeanWrapper src = new BeanWrapperImpl(source); //해당 객체의 필드값을 세팅하고 가져올 수 있는 BeanWrapper. (해당 방법을 사용하여야지 범용적으로 사용 가능.)
        final BeanWrapper dest = new BeanWrapperImpl(destination); //해당 객체의 필드값을 세팅하고 가져올 수 있는 BeanWrapper. (해당 방법을 사용하여야지 범용적으로 사용 가능.)


        for (final Field property : source.getClass().getDeclaredFields()) {
            Object sourceProperty = src.getPropertyValue(property.getName()); //BeanWrapper 를 사용하지 않으면, get[필드명] 과 같이 사용해야 하므로, 이미 해당 클래스를 사용할 수 있는 타입이 정해져 버린다..
            if (sourceProperty != null && !(sourceProperty instanceof Collection<?>)) {
                dest.setPropertyValue(property.getName(), sourceProperty);
            }
        }

        return destination;
    }
}
