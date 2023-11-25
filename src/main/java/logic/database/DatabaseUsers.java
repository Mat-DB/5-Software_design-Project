package logic.database;

import logic.entities.User;

public class DatabaseUsers extends Database<User> {
    public DatabaseUsers() {}

    public void addTicket(User user){
        int id = (user.getName()+user.getID()).hashCode();
        addEntry(id, user);
    }

    public int userNameOccurences(String name, int id){
        int hash = (name+id).hashCode();
        if (db.get(hash) != null){
            userNameOccurences(name, id+1);
        }
        return id;
    }
}
