package logic.database;

import java.util.HashMap;
import java.util.logging.Logger;

public abstract class Database<T> {
    protected HashMap<Integer, T> db;

    public Database() {
        db = new HashMap<>();
    }

    public void addEntry(int id, T t) {
        Logger logger = Logger.getLogger(Database.class.getName());
        logger.fine("id: " + id);
        db.put(id, t);
    }

    public T getEntry(int id){
        return db.get(id);
    }
}
