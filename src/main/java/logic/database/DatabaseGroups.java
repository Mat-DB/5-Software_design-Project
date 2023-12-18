package logic.database;

import logic.groups.Group;

import java.util.ArrayList;

public class DatabaseGroups extends Database<Group> {
    private static DatabaseGroups instance;
    private DatabaseGroups() {
        super();
    }

    public static DatabaseGroups getGroupDatabase() {
        if (instance == null) {
            instance = new DatabaseGroups();
        }
        return instance;
    }

    public void addGroup(Group group) {
        this.addEntry(getGroupHash(group.getName()), group);
        observable.firePropertyChange("new group", null, group);
    }

    public void removeGroup(int groupHash) {
        observable.firePropertyChange("remove group", getGroup(groupHash), null);
        removeEntry(groupHash);
    }

    public Group getGroup(String groupName) {
        return this.getEntry(getGroupHash(groupName));
    }

    public Group getGroup(int groupHash) {
        return this.getEntry(groupHash);
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        for (int key : db.keySet()) {
            groups.add(this.getEntry(key));
        }
        return groups;
    }

    public int getGroupHash(String groupName) {
        return groupName.hashCode();
    }
}
