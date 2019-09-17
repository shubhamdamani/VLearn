package com.example.vlearn.object;

import java.util.HashMap;

public class key_Topic {

    static HashMap<Character,String> TopicToKey;
    static{
        TopicToKey = new HashMap<>();
        TopicToKey.put('A',"Physics");
        TopicToKey.put('B',"Maths");
        TopicToKey.put('C',"Computer");
        TopicToKey.put('D',"Science");
        TopicToKey.put('E',"Politics");
        TopicToKey.put('F',"Business");
        TopicToKey.put('G',"Technology");
        TopicToKey.put('H',"Sports");
        TopicToKey.put('I',"Movies");
        TopicToKey.put('J',"Music");
    }
    public static String getTopic(char key) {
        return TopicToKey.get(key);
    }
}
