package com.au.utils.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EmptyKeyFactoryTest {

    private static final String ITEM_1 = "item1";

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";

    private EmptyKeyFactoryTestImpl sut;

    @Before
    public void init() {
        sut = new EmptyKeyFactoryTestImpl();
        sut.items.put(KEY_1, ITEM_1);
        sut.init();
    }

    @Test
    public void getItem_shouldReturnValueFromItemsTest() {
        String actualValue = sut.getItem(KEY_1);

        Assert.assertEquals(ITEM_1, actualValue);
    }

    @Test
    public void getItem_shouldReturnNullValueForNotExistingKeyTest() {
        String actualResult = sut.getItem(KEY_2);

        Assert.assertNull(actualResult);
    }
}

class EmptyKeyFactoryTestImpl extends EmptyKeyFactory<String, String> {

    Map<String, String> items = new HashMap<>();

    @Override
    protected Map<String, String> initItems() {
        return items;
    }

}
