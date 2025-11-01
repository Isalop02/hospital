package co.edu.umanizales.hospital.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling CSV file operations
 * Manages reading and writing data to CSV files
 */
@Slf4j
@Service
public class CsvService {
    private static final String DATA_DIR = "data";

    public CsvService() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        try {
            Path path = Paths.get(DATA_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("Data directory created: {}", DATA_DIR);
            }
        } catch (IOException e) {
            log.error("Error creating data directory", e);
        }
    }

    /**
     * Read all records from a CSV file
     */
    public List<String[]> readCsv(String filename) {
        List<String[]> records = new ArrayList<>();
        String filepath = DATA_DIR + "/" + filename + ".csv";

        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            records = reader.readAll();
        } catch (IOException | com.opencsv.exceptions.CsvException e) {
            log.warn("CSV file not found or error reading: {}", filepath);
        }
        return records;
    }

    /**
     * Write records to a CSV file
     */
    public void writeCsv(String filename, List<String[]> records) {
        String filepath = DATA_DIR + "/" + filename + ".csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath))) {
            writer.writeAll(records);
            log.info("CSV file written successfully: {}", filepath);
        } catch (IOException e) {
            log.error("Error writing CSV file: {}", filepath, e);
        }
    }

    /**
     * Append a record to a CSV file
     */
    public void appendToCsv(String filename, String[] record) {
        String filepath = DATA_DIR + "/" + filename + ".csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath, true))) {
            writer.writeNext(record);
            log.info("Record appended to CSV file: {}", filepath);
        } catch (IOException e) {
            log.error("Error appending to CSV file: {}", filepath, e);
        }
    }

    /**
     * Find a record by ID in CSV file
     */
    public String[] findRecordById(String filename, String id, int idColumnIndex) {
        List<String[]> records = readCsv(filename);

        for (String[] record : records) {
            if (record.length > idColumnIndex && record[idColumnIndex].equals(id)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Delete a record from CSV file by ID
     */
    public boolean deleteRecordById(String filename, String id, int idColumnIndex) {
        List<String[]> records = readCsv(filename);
        
        // Use a final array to track if the record was found
        final boolean[] found = {false};
        
        records.removeIf(record -> {
            if (record.length > idColumnIndex && record[idColumnIndex].equals(id)) {
                found[0] = true;
                return true;
            }
            return false;
        });

        if (found[0]) {
            writeCsv(filename, records);
        }
        return found[0];
    }

    /**
     * Update a record in CSV file by ID
     */
    public boolean updateRecordById(String filename, String id, String[] newRecord, int idColumnIndex) {
        List<String[]> records = readCsv(filename);
        boolean found = false;

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).length > idColumnIndex && records.get(i)[idColumnIndex].equals(id)) {
                records.set(i, newRecord);
                found = true;
                break;
            }
        }

        if (found) {
            writeCsv(filename, records);
        }
        return found;
    }
}
