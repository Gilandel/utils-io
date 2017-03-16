/*-
 * #%L
 * utils-io
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.ComparisonFailure;

import fr.landel.utils.commons.function.TriFunction;

/**
 * Abstract for tests
 *
 * @since Jul 31, 2016
 * @author Gilles
 *
 */
public abstract class AbstractTest {

    /**
     * Function to manage the creation of Junit exception
     */
    public static final TriFunction<Boolean, String, String, AssertionError> JUNIT_ERROR = (catched, expected, actual) -> {
        if (catched) {
            return new ComparisonFailure("The exception message don't match.", expected, actual);
        } else {
            return new AssertionError("The expected exception never comes up.");
        }
    };

    /**
     * Utility method to check private constructor of utility class
     * 
     * @param clazz
     *            the class to check
     * @return true if ok
     * @throws AssertionError
     *             if preconditions mismatch
     */
    public static boolean checkPrivateConstructor(final Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        assertEquals(1, constructors.length);
        assertTrue(Modifier.isPrivate(constructors[0].getModifiers()));

        constructors[0].setAccessible(true);

        try {
            constructors[0].newInstance();
            return false;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            assertTrue(InvocationTargetException.class.isAssignableFrom(e.getClass()));
            final InvocationTargetException exception = (InvocationTargetException) e;
            assertNotNull(exception.getTargetException());
            return UnsupportedOperationException.class.isAssignableFrom(exception.getTargetException().getClass());
        }
    }
}
