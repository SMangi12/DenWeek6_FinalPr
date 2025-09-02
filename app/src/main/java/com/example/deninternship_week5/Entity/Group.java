//package com.example.deninternship_week5.Entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Group {
//    private String groupId;
//    private String groupName;
//    private List<String> members; // store userIds
//    private long createdAt;
//
//    public Group() { }
//
//    public Group(String groupId, String groupName, List<String> members, long createdAt) {
//        this.groupId = groupId;
//        this.groupName = groupName;
//        this.members = members;
//        this.createdAt = createdAt;
//    }
//
//    public String getGroupId() {
//        return groupId;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public List<String> getMembers() {
//        return members;
//    }
//
//    public long getCreatedAt() {
//        return createdAt;
//    }
//}

package com.example.deninternship_week5.Entity;

import java.util.Map;

public class Group {
    private String groupId;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String groupName;
    private Map<String, Boolean> members; // store userIds as keys, true as value
    private long createdAt;
    private Map<String, Message> messages; // store messages by ID

    public Group() { }

    public Group(String groupId, String groupName, Map<String, Boolean> members, long createdAt, Map<String, Message> messages) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = members;
        this.createdAt = createdAt;
        this.messages = messages;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Map<String, Boolean> getMembers() {
        return members;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }
}
