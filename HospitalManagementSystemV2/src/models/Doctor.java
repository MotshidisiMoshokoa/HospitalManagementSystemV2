package models;

public class Doctor {

    private int doctorId;
    private String fullName;
    private String specialty;
    private String phone;
    private String email;
    private String availability;

    public Doctor(int doctorId, String fullName, String specialty,
                  String phone, String email, String availability) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.availability = availability;
    }

    public Doctor(String fullName, String specialty,
                  String phone, String email, String availability) {
        this.fullName = fullName;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
        this.availability = availability;
    }

    public int getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
    public String getSpecialty() { return specialty; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAvailability() { return availability; }
}