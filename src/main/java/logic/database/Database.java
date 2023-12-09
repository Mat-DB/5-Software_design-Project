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

    protected void addEntry(int id, T t) {
        Logger logger = Logger.getLogger(Database.class.getName());
        logger.fine("id: " + id);
        db.put(id, t);
    }

    protected void removeEntry(int id) {
        db.remove(id);
    }

    protected T getEntry(int id){
        return db.get(id);
    }

    public void addObserver(PropertyChangeListener observer) {
        observable.addPropertyChangeListener(observer);
    }

    public void removeObserver(PropertyChangeListener observer) {
        observable.removePropertyChangeListener(observer);
    }
}
