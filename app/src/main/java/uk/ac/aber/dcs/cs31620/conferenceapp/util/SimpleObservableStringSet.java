package uk.ac.aber.dcs.cs31620.conferenceapp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleObservableStringSet extends HashSet<String> {
    private List<Observer> m_observers;
    private boolean m_allowNotifications;

    public SimpleObservableStringSet(Set<String> set) {
        super(set);
        m_observers = new ArrayList<>();
        // Only allow notifications to be sent after m_observers has been made.
        m_allowNotifications = true;
    }

    @Override
    public boolean add(String string) {
        boolean returnValue = super.add(string);
        notifyAllAddObservers(string);
        return returnValue;
    }

    private void notifyAllAddObservers(String string) {
        // Do not use modern for each loop as it throws a concurrent modification error.
        if (m_allowNotifications) {
            for (int ii = 0; ii < m_observers.size(); ii++) {
                m_observers.get(ii).observedAddUpdate(string);
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        boolean returnValue = super.remove(o);
        notifyAllRemoveObservers((String) o);
        return returnValue;
    }

    private void notifyAllRemoveObservers(String string) {
        // Do not use modern for each loop as it throws a concurrent modification error.
        if (m_allowNotifications) {
            for (int ii = 0; ii < m_observers.size(); ii++) {
                m_observers.get(ii).observedRemoveUpdate(string);
            }
        }
    }


    public void addObserver(Observer o) {
        m_observers.add(o);
    }
}