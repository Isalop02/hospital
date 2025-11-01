package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Room operations
 */
@Slf4j
@Service
public class RoomService {
    private final CsvService csvService;
    private static final String FILENAME = "rooms";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public RoomService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new room
     */
    public Room createRoom(Room room) {
        room.setRoomId(UUID.randomUUID().toString());
        room.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        
        String[] record = roomToArray(room);
        csvService.appendToCsv(FILENAME, record);
        log.info("Room created: {}", room.getRoomId());
        return room;
    }

    /**
     * Get room by ID
     */
    public Room getRoomById(String roomId) {
        String[] record = csvService.findRecordById(FILENAME, roomId, 0);
        if (record != null) {
            return arrayToRoom(record);
        }
        return null;
    }

    /**
     * Get all rooms
     */
    public List<Room> getAllRooms() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Room> rooms = new ArrayList<>();
        
        for (String[] record : records) {
            rooms.add(arrayToRoom(record));
        }
        return rooms;
    }

    /**
     * Update room
     */
    public Room updateRoom(String roomId, Room room) {
        room.setRoomId(roomId);
        String[] record = roomToArray(room);
        
        if (csvService.updateRecordById(FILENAME, roomId, record, 0)) {
            log.info("Room updated: {}", roomId);
            return room;
        }
        return null;
    }

    /**
     * Delete room
     */
    public boolean deleteRoom(String roomId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, roomId, 0);
        if (deleted) {
            log.info("Room deleted: {}", roomId);
        }
        return deleted;
    }

    /**
     * Get available rooms
     */
    public List<Room> getAvailableRooms() {
        List<Room> allRooms = getAllRooms();
        List<Room> available = new ArrayList<>();
        
        for (Room room : allRooms) {
            if (room.isAvailable()) {
                available.add(room);
            }
        }
        return available;
    }

    /**
     * Get rooms by type
     */
    public List<Room> getRoomsByType(Room.RoomType type) {
        List<Room> allRooms = getAllRooms();
        List<Room> result = new ArrayList<>();
        
        for (Room room : allRooms) {
            if (room.getRoomType() == type) {
                result.add(room);
            }
        }
        return result;
    }

    /**
     * Convert Room object to CSV array
     */
    private String[] roomToArray(Room room) {
        return new String[]{
            room.getRoomId(),
            room.getRoomNumber(),
            room.getRoomType() != null ? room.getRoomType().toString() : "",
            room.getRegistrationDate(),
            room.getStatus() != null ? room.getStatus().toString() : "",
            room.getCurrentPatientId(),
            String.valueOf(room.getCapacity()),
            String.valueOf(room.getDailyRate())
        };
    }

    /**
     * Convert CSV array to Room object
     */
    private Room arrayToRoom(String[] record) {
        if (record.length < 8) return null;

        Room room = new Room();
        room.setRoomId(record[0]);
        room.setRoomNumber(record[1]);
        room.setRoomType(!record[2].isEmpty() ? Room.RoomType.valueOf(record[2]) : null);
        room.setRegistrationDate(record[3]);
        room.setStatus(!record[4].isEmpty() ? Room.RoomStatus.valueOf(record[4]) : null);
        room.setCurrentPatientId(record[5]);
        room.setCapacity(!record[6].isEmpty() ? Integer.parseInt(record[6]) : 0);
        room.setDailyRate(!record[7].isEmpty() ? Double.parseDouble(record[7]) : 0.0);

        return room;
    }
}
