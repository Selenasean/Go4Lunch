package fr.selquicode.go4lunch.ui.utils;

public class IdCreator {

    /**
     * To create a unique Id from 2 existing id
     * @param userId id1 - type String
     * @param workmateId id2 - type String
     * @return concatenated id - type String
     */
    public static String createMessageUid(String userId, String workmateId){
        return userId.compareTo(workmateId) > 0 ? userId + "_" + workmateId : workmateId + "_" + userId;
    }
}
