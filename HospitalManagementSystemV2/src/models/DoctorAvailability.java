package models;

public class DoctorAvailability {

    private int doctorId;
    private String date;
    private String time;

    public DoctorAvailability(int doctorId, String date, String time) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}