package fr.landel.utils.io;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Check {@link EncodingUtils}
 *
 * @since Mar 16, 2017
 * @author Gilles
 *
 */
public class EncodingUtilsTest extends AbstractTest {

    /**
     * Test constructor for {@link EncodingUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(EncodingUtils.class));
    }
}
