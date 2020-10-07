package com.example.studybee;

import java.util.List;

import us.zoom.sdk.Alternativehost;
import us.zoom.sdk.MobileRTCDialinCountry;

public interface MeetingItem {
    String getMeetingTopic();

    boolean setMeetingTopic(String var1);

    long getStartTime();

    boolean setStartTime(long var1);

    int getDurationInMinutes();

    boolean setDurationInMinutes(int var1);

    String getPassword();

    void setPassword(String var1);

    boolean getCanJoinBeforeHost();

    void setCanJoinBeforeHost(boolean var1);

    boolean isUsePmiAsMeetingID();

    boolean setUsePmiAsMeetingID(boolean var1);

    String getTimeZoneId();

    boolean setTimeZoneId(String var1);

    boolean isHostVideoOff();

    void setHostVideoOff(boolean var1);

    boolean isAttendeeVideoOff();

    void setAttendeeVideoOff(boolean var1);

    us.zoom.sdk.MeetingItem.AudioType getAudioType();

    void setAudioType(us.zoom.sdk.MeetingItem.AudioType var1);

    String getThirdPartyAudioInfo();

    void setThirdPartyAudioInfo(String var1);

    long getMeetingNumber();

    long getMeetingUniqueId();

    boolean isRecurringMeeting();

    int getRepeatType();

    boolean setRepeatType(int var1);

    long getRepeatEndTime();

    boolean setRepeatEndTime(long var1);

    String getMeetingId();

    boolean isPersonalMeeting();

    boolean isWebinarMeeting();

    String getInvitationEmailContentWithTime();

    boolean isOnlySignUserCanJoin();

    void setOnlySignUserCanJoin(boolean var1);

    void setSpecifiedDomains(List<String> var1);

    List<String> getSpecifiedDomains();

    String getScheduleForHostEmail();

    boolean setScheduleForHostEmail(String var1);

    us.zoom.sdk.MeetingItem.AutoRecordType getAutoRecordType();

    void setAutoRecordType(us.zoom.sdk.MeetingItem.AutoRecordType var1);

    void setHostInChinaEnabled(boolean var1);

    boolean isHostInChinaEnabled();

    void setAvailableDialinCountry(MobileRTCDialinCountry var1);

    MobileRTCDialinCountry getAvailableDialinCountry();

    void setEnableWaitingRoom(boolean var1);

    boolean isEnableWaitingRoom();

    void setEnableLanguageInterpretation(boolean var1);

    boolean isEnableLanguageInterpretation();

    void setEnableMeetingToPublic(boolean var1);

    boolean isEnableMeetingToPublic();

    void setAlternativeHostList(List<Alternativehost> var1);

    List<Alternativehost> getAlternativeHostList();

    public static enum AutoRecordType {
        AutoRecordType_Disabled,
        AutoRecordType_LocalRecord,
        AutoRecordType_CloudRecord;

        private AutoRecordType() {
        }
    }

    public static enum AudioType {
        AUDIO_TYPE_VOIP,
        AUDIO_TYPE_TELEPHONY,
        AUDIO_TYPE_VOIP_AND_TELEPHONEY,
        AUDIO_TYPE_THIRD_PARTY_AUDIO;

        private AudioType() {
        }
    }

    public interface RepeatType {
        int None = 0;
        int EveryDay = 1;
        int EveryWeek = 2;
        int Every2Weeks = 3;
        int EveryMonth = 4;
        int EveryYear = 5;
    }
}

