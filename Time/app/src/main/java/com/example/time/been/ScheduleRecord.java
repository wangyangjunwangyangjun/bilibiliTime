package com.example.time.been;

public class ScheduleRecord {
    private String name;
    private String type;
    private String level;
    private String message;
    private String datetime;
    private String alarmClock;
    private int alarmClockOpen;
    private String location;
    private int locationOpen;
    private byte[] photo;
    private ScheduleRecord(){}
    public static class scheduleRecordBuilder{
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        public scheduleRecordBuilder basicInfo(String name, String type){
            scheduleRecord.name = name;
            scheduleRecord.type = type;
            return this;
        }
        public scheduleRecordBuilder level(String level){
            scheduleRecord.level = level;
            return this;
        }
        public scheduleRecordBuilder message(String message){
            scheduleRecord.message = message;
            return this;
        }
        public scheduleRecordBuilder datetime(String datetime){
            scheduleRecord.datetime = datetime;
            return this;
        }
        public scheduleRecordBuilder alarmClock(String alarmClock){
            scheduleRecord.alarmClock = alarmClock;
            return this;
        }
        public scheduleRecordBuilder alarmClockOpen(int alarmClockOpen){
            scheduleRecord.alarmClockOpen = alarmClockOpen;
            return this;
        }
        public scheduleRecordBuilder location(String location){
            scheduleRecord.location = location;
            return this;
        }
        public scheduleRecordBuilder locationOpen(int locationOpen){
            scheduleRecord.locationOpen = locationOpen;
            return this;
        }
        public scheduleRecordBuilder photo(byte[] photo){
            scheduleRecord.photo = photo;
            return this;
        }
        public ScheduleRecord build(){
            return scheduleRecord;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getAlarmClock() {
        return alarmClock;
    }

    public void setAlarmClock(String alarmClock) {
        this.alarmClock = alarmClock;
    }

    public int isAlarmClockOpen() {
        return alarmClockOpen;
    }

    public void setAlarmClockOpen(int alarmClockOpen) {
        this.alarmClockOpen = alarmClockOpen;
    }

    public int isLocationOpen() {
        return locationOpen;
    }

    public void setLocationOpen(int locationOpen) {
        this.locationOpen = locationOpen;
    }
}
