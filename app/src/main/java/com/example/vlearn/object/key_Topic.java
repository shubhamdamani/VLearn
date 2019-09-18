package com.example.vlearn.object;

import java.util.HashMap;

public class key_Topic {

    static HashMap<Character,String> KeyToTopic;
    static{
        KeyToTopic = new HashMap<>();
        KeyToTopic.put('A',"Physics");
        KeyToTopic.put('B',"Maths");
        KeyToTopic.put('C',"Computer");
        KeyToTopic.put('D',"Science");
        KeyToTopic.put('E',"Politics");
        KeyToTopic.put('F',"Business");
        KeyToTopic.put('G',"Technology");
        KeyToTopic.put('H',"Sports");
        KeyToTopic.put('I',"Movies");
        KeyToTopic.put('J',"Music");
    }

    static HashMap<String,Character> TopicToKey;
    static{
        TopicToKey = new HashMap<>();
        TopicToKey.put("Physics",'A');
        TopicToKey.put("Maths",'B');
        TopicToKey.put("Computer",'C');
        TopicToKey.put("Science",'D');
        TopicToKey.put("Politics",'E');
        TopicToKey.put("Business",'F');
        TopicToKey.put("Technology",'G');
        TopicToKey.put("Sports",'H');
        TopicToKey.put("Movies",'I');
        TopicToKey.put("Music",'J');
    }

    public static String getTopic(char key) {
        return KeyToTopic.get(key);
    }

    public static String getTopicToKey(String topic) {
        return TopicToKey.get(topic).toString();
    }
}
