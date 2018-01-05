/*
 * #%L
 * utils-io
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage closeables.
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class CloseableManager {

    private static final ConcurrentMap<Integer, List<Closeable>> CLOSEABLES = new ConcurrentHashMap<>();

    /**
     * Error close
     */
    private static final String ERROR_CLOSEABLE = "closeable can't be closed...";

    /**
     * Error not closeable
     */
    private static final String ERROR_CLOSEABLE_NOT_CLOSEABLE = "closeable can't be closed";

    private static Logger logger = LoggerFactory.getLogger(CloseableManager.class.getName());

    /**
     * Constructor.
     * 
     */
    private CloseableManager() {
        throw new UnsupportedOperationException();
    }

    /**
     * Define the current logger.
     * 
     * @param logger
     *            The logger
     */
    public static void defineLogger(final Logger logger) {
        CloseableManager.logger = logger;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param clazz
     *            The associated class
     * @return true, if found
     */
    public static boolean isCloseable(final Class<?> clazz) {
        if (clazz != null) {
            return CLOSEABLES.containsKey(clazz.hashCode());
        }
        return false;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param closeable
     *            The associated closeable
     * @return true, if found
     */
    public static boolean isCloseable(final Closeable closeable) {
        if (closeable != null) {
            return CLOSEABLES.containsKey(closeable.hashCode());
        }
        return false;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param hashcode
     *            The associated hashcode
     * @return true, if found
     */
    public static boolean isCloseable(final Integer hashcode) {
        if (hashcode != null) {
            return CLOSEABLES.containsKey(hashcode);
        }
        return false;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param fileName
     *            The associated filename
     * @return true, if found
     */
    public static boolean isCloseable(final String fileName) {
        if (fileName != null) {
            return isCloseable(new File(fileName));
        }
        return false;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param file
     *            The associated file
     * @return true, if found
     */
    public static boolean isCloseable(final File file) {
        if (file != null) {
            return CLOSEABLES.containsKey(file.getAbsolutePath().hashCode());
        }
        return false;
    }

    /**
     * Check if a closeable is already created.
     * 
     * @param url
     *            The associated url
     * @return true, if found
     */
    public static boolean isCloseable(final URL url) {
        if (url != null) {
            return CLOSEABLES.containsKey(url.hashCode());
        }
        return false;
    }

    /**
     * Add closeable to the list associated to the path.
     * 
     * @param fileName
     *            The path to associated file to the closeable
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final String fileName, final C closeable) {
        if (fileName != null && closeable != null) {
            return addCloseable(new File(fileName), closeable);
        }
        return null;
    }

    /**
     * Add closeable to the list associated closeable to the hashcode.
     * 
     * @param hashcode
     *            The hashcode associated to the closeable
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final Integer hashcode, final C closeable) {
        if (hashcode != null && closeable != null) {
            if (!CLOSEABLES.containsKey(hashcode)) {
                CLOSEABLES.put(hashcode, new ArrayList<Closeable>());
            }
            CLOSEABLES.get(hashcode).add(closeable);
            return closeable;
        }
        return null;
    }

    /**
     * Add closeable to the list associated closeable to the hashcode.
     * 
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final C closeable) {
        if (closeable != null) {
            if (!CLOSEABLES.containsKey(closeable.hashCode())) {
                CLOSEABLES.put(closeable.hashCode(), new ArrayList<Closeable>());
            }
            CLOSEABLES.get(closeable.hashCode()).add(closeable);
            return closeable;
        }
        return null;
    }

    /**
     * Add closeable to the list associated to the file.
     * 
     * @param file
     *            The associated file to the closeable
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final File file, final C closeable) {
        if (file != null) {
            return addCloseable(file.getAbsolutePath().hashCode(), closeable);
        }
        return null;
    }

    /**
     * Add closeable to the list associated to the url.
     * 
     * @param url
     *            The associated url to the closeable
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final URL url, final C closeable) {
        if (url != null) {
            return addCloseable(url.hashCode(), closeable);
        }
        return null;
    }

    /**
     * Add closeable to the list associated to the class
     * 
     * @param clazz
     *            The class associated to the closeable
     * @param closeable
     *            The closeable to be added
     * @param <C>
     *            The closeable type
     * @return the input closeable parameter
     */
    public static <C extends Closeable> C addCloseable(final Class<?> clazz, final C closeable) {
        if (clazz != null) {
            return addCloseable(clazz.hashCode(), closeable);
        }
        return null;
    }

    /**
     * Reverse the order of the element of the list
     * 
     * @param closeables
     *            The list to be reversed
     * @return The reverse list
     */
    private static List<Closeable> reverseList(final List<Closeable> closeables) {
        final List<Closeable> reverseCloseables = new ArrayList<Closeable>(closeables);
        Collections.reverse(reverseCloseables);
        return reverseCloseables;
    }

    /**
     * Close all managed closeables
     */
    public static synchronized void closeAll() {
        for (Entry<Integer, List<Closeable>> entry : CLOSEABLES.entrySet()) {
            final List<Closeable> reversedCloseables = reverseList(entry.getValue());
            for (Closeable closeable : reversedCloseables) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    logger.error(String.format(ERROR_CLOSEABLE, entry.getKey()), e);
                }
            }
            entry.getValue().clear();
        }
        CLOSEABLES.clear();
    }

    /**
     * Close all managed closeables associated to the path
     * 
     * @param fileName
     *            The path of the associated file to the closeable
     */
    public static void close(final String fileName) {
        if (fileName != null) {
            close(new File(fileName));
        }
    }

    /**
     * Close all managed closeables associated to the file
     * 
     * @param file
     *            The associated file to the closeable
     */
    public static void close(final File file) {
        if (file != null) {
            close(file.getAbsolutePath().hashCode());
        }
    }

    /**
     * Close all managed closeables associated to the hashcode
     * 
     * @param hashcode
     *            The hashcode associated to the closeable
     */
    public static synchronized void close(final Integer hashcode) {
        if (hashcode != null && CLOSEABLES.containsKey(hashcode)) {
            final List<Closeable> reversedCloseables = reverseList(CLOSEABLES.get(hashcode));
            for (Closeable closeable : reversedCloseables) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    logger.error(String.format(ERROR_CLOSEABLE, hashcode), e);
                }
            }
            CLOSEABLES.get(hashcode).clear();
            CLOSEABLES.remove(hashcode);
        }
    }

    /**
     * Close all managed closeables associated to the class
     * 
     * @param clazz
     *            The class associated to the closeable
     */
    public static void close(final Class<?> clazz) {
        if (clazz != null) {
            close(clazz.hashCode());
        }
    }

    /**
     * Close all managed closeables associated to the url
     * 
     * @param url
     *            The url associated to the closeable
     */
    public static void close(final URL url) {
        if (url != null) {
            close(url.hashCode());
        }
    }

    /**
     * Close the closeable
     * 
     * @param closeable
     *            The closeable to close
     */
    public static void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                final Optional<Integer> hashcode = CLOSEABLES.entrySet().stream().filter((e) -> e.getValue().contains(closeable))
                        .map((e) -> e.getKey()).findFirst();
                if (hashcode.isPresent()) {
                    close(hashcode.get());
                } else {
                    closeable.close();
                }
            } catch (IOException e) {
                logger.error(ERROR_CLOSEABLE_NOT_CLOSEABLE, e);
            }
        }
    }
}
