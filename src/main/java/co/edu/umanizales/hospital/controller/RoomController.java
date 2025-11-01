package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Room;
import co.edu.umanizales.hospital.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
        } catch (Exception e) {
            log.error("Error creating room", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable String roomId) {
        try {
            Room room = roomService.getRoomById(roomId);
            return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving room", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        try {
            return ResponseEntity.ok(roomService.getAllRooms());
        } catch (Exception e) {
            log.error("Error retrieving all rooms", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody Room room) {
        try {
            Room updated = roomService.updateRoom(roomId, room);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating room", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {
        try {
            return roomService.deleteRoom(roomId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting room", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        try {
            return ResponseEntity.ok(roomService.getAvailableRooms());
        } catch (Exception e) {
            log.error("Error retrieving available rooms", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String type) {
        try {
            return ResponseEntity.ok(roomService.getRoomsByType(Room.RoomType.valueOf(type)));
        } catch (Exception e) {
            log.error("Error retrieving rooms by type", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
