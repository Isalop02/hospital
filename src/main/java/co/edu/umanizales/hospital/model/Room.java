package co.edu.umanizales.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Room class representing hospital rooms
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Registrable, Attendable {
    private String roomId;
    private String roomNumber;
    private RoomType roomType;
    private String registrationDate;
    private RoomStatus status;
    private String currentPatientId;
    private int capacity;
    private double dailyRate;

    public enum RoomType {
        GENERAL, INTENSIVE_CARE, PRIVATE, PEDIATRIC
    }

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE, CLOSED
    }

    @Override
    public String getId() {
        return roomId;
    }

    @Override
    public void setId(String id) {
        this.roomId = id;
    }

    @Override
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String getAttendanceType() {
        return "ROOM_ACCOMMODATION";
    }

    @Override
    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE;
    }

    public double calculateStayCost(int days) {
        return dailyRate * days;
    }
}
