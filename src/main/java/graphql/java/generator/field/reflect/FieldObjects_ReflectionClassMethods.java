package graphql.java.generator.field.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graphql.java.generator.field.FieldObjectsStrategy;

/**
 * A reflection based, java Method centric class for generating data
 * on GraphQL fields.  This is as opposed to a Field centric way of generating
 * the same fields.
 * TODO re-merge into FieldObjects_ReflectionClass that combines the output
 * of both FieldObjects_ReflectionClass (Fields&&Methods)
 * @author dwinsor
 *
 */
public class FieldObjects_ReflectionClassMethods implements FieldObjectsStrategy {
    
    /**
     * Looks at {@link java.lang.reflect.Method}s and returns those
     * which are suitable.
     */
    @Override
    public List<Object> getFieldRepresentativeObjects(Object object) {
        if (!(object instanceof Class<?>)) {
            return null;
        }
        Class<?> clazz = (Class<?>) object;
        List<Object> fieldObjects = new ArrayList<Object>();
        Method[] methods = clazz.getMethods();
        for (int index = 0; index < methods.length; ++index) {
            Method method = methods[index];
            if (method.isSynthetic()) {
                continue;
            }
            String methodName = method.getName();
            if (!(methodName.startsWith("get") || methodName.startsWith("is"))) {
                continue;
            }
            if (methodName.equals("getClass")) {
                continue;
            }
            fieldObjects.add(method);
        }
        return fieldObjects;
    }
}