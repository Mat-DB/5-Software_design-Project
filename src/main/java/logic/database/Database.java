package logic.database;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class Database<T> {
    protected HashMap<Integer, T> db;
    protected PropertyChangeSupport observable;

    protected Database() {
        db = new HashMap<>();
        observable = new PropertyChangeSupport(this);
    }

    protected void addEntry(int key, T t) {
        Logger logger = Logger.getLogger(Database.class.getName());
        logger.fine("id: " + key);
        db.put(key, t);
    }

    protected void removeEntry(int key) {
        db.remove(key);
    }

    protected T getEntry(int key){
        return db.get(key);
    }

    public void addObserver(PropertyChangeListener observer) {
        observable.addPropertyChangeListener(observer);
    }

    public void removeObserver(PropertyChangeListener observer) {
        observable.removePropertyChangeListener(observer);
    }
}
