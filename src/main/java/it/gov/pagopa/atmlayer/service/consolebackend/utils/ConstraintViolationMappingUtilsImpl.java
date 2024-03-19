package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Singleton
public class ConstraintViolationMappingUtilsImpl implements ConstraintViolationMappingUtils {

    public List<String> extractErrorMessages(Set<ConstraintViolation<?>> errors) {
        return errors.stream().map(this::extractErrorMessage).toList();
    }

    public String extractErrorMessage(ConstraintViolation<?> error) {
        PathImpl errorNodes = (PathImpl) error.getPropertyPath();
        NodeImpl leafNode = errorNodes.getLeafNode();
        NodeImpl field = leafNode.isInIterable() ? leafNode.getParent() : leafNode;
        return field.asString().concat(" ").concat(error.getMessage());

    }

    public static <T> T buildObjectFromMap(Class<T> clazz, Map<String, Object> map) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T obj = constructor.newInstance();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            if (value instanceof Map) {
                // If the value is a nested map, recursively build the object
                Class<?> fieldType = field.getType();
                field.set(obj, buildObjectFromMap(fieldType, (Map<String, Object>) value));
            } else {
                field.set(obj, value);
            }
        }

        return obj;
    }
}
