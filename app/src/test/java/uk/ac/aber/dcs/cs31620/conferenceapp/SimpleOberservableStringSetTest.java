package uk.ac.aber.dcs.cs31620.conferenceapp;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import uk.ac.aber.dcs.cs31620.conferenceapp.util.Observer;
import uk.ac.aber.dcs.cs31620.conferenceapp.util.SimpleObservableStringSet;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SimpleOberservableStringSetTest implements Observer {
    private int m_addCalledCount;
    private int m_removeCalledCount;
    private SimpleObservableStringSet m_testSet;

    @Before
    public void setUp() {
        m_addCalledCount = 0;
        m_removeCalledCount = 0;
        m_testSet = new SimpleObservableStringSet(new HashSet<>());
        m_testSet.addObserver(this);
    }

    @Test
    public void test_thatSimpleObservableStringSetNotifiesObserversOnAdd() {
        m_testSet.add("test1");
        assertEquals(m_addCalledCount, 1);
    }

    @Test
    public void test_thatSimpleObservableStringSetNotifiesObserversOnRemove() {
        final String test1 = "test1";
        m_testSet.add(test1);
        m_testSet.remove(test1);
        assertEquals(m_addCalledCount, 1);
        assertEquals(m_removeCalledCount, 1);
    }

    @Test
    public void test_notifiesMultipleTimesForMultipleAdds() {
        m_testSet.add("test1");
        m_testSet.add("test2");
        m_testSet.add("test3");
        assertEquals(m_addCalledCount, 3);
    }

    @Test
    public void test_notifiesMultipleTimesForMultipleRemoves() {
        final String test1 = "test1";
        m_testSet.add(test1);
        final String test2 = "test2";
        m_testSet.add(test2);
        final String test3 = "test3";
        m_testSet.add(test3);
        m_testSet.remove(test1);
        m_testSet.remove(test2);
        m_testSet.remove(test3);
        assertEquals(m_addCalledCount, 3);
        assertEquals(m_removeCalledCount, 3);
    }

    // Some basic tests here to ensure that some set functionality is still possible via this class
    @Test
    public void test_setSizeOption() {
        for (int i = 0; i < 10; ++i) {
            m_testSet.add("" + i);
            assertEquals(m_testSet.size(), i + 1);
        }
    }

    @Test
    public void test_setUniquenessIsGuarenteed() {
        final String test1 = "test1";
        m_testSet.add(test1);
        m_testSet.add(test1);
        assertEquals(m_testSet.size(), 1);

        // Ensure the value in the set is test1
        for (String wannabeTest1 : m_testSet) {
            assertEquals(wannabeTest1, test1);
        }
    }

    @Test
    public void test_setRetrievalViaIteratorStillWorks() {
        final String test1 = "test1";
        m_testSet.add(test1);
        for (String wannabeTest1 : m_testSet) {
            assertEquals(wannabeTest1, test1);
        }

    }

    @Override
    public void observedAddUpdate(String sessionId) {
        m_addCalledCount += 1;
    }

    @Override
    public void observedRemoveUpdate(String sessionId) {
        m_removeCalledCount += 1;
    }
}