package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Invoice;
import co.edu.umanizales.hospital.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing Invoice operations
 */
@Slf4j
@Service
public class InvoiceService {
    private final CsvService csvService;
    private static final String FILENAME = "invoices";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InvoiceService(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Create a new invoice
     */
    public Invoice createInvoice(Invoice invoice) {
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setRegistrationDate(LocalDate.now().format(DATE_FORMATTER));
        invoice.setDate(LocalDate.now());
        
        String[] record = invoiceToArray(invoice);
        csvService.appendToCsv(FILENAME, record);
        log.info("Invoice created: {}", invoice.getInvoiceId());
        return invoice;
    }

    /**
     * Get invoice by ID
     */
    public Invoice getInvoiceById(String invoiceId) {
        String[] record = csvService.findRecordById(FILENAME, invoiceId, 0);
        if (record != null) {
            return arrayToInvoice(record);
        }
        return null;
    }

    /**
     * Get all invoices
     */
    public List<Invoice> getAllInvoices() {
        List<String[]> records = csvService.readCsv(FILENAME);
        List<Invoice> invoices = new ArrayList<>();
        
        for (String[] record : records) {
            invoices.add(arrayToInvoice(record));
        }
        return invoices;
    }

    /**
     * Update invoice
     */
    public Invoice updateInvoice(String invoiceId, Invoice invoice) {
        invoice.setInvoiceId(invoiceId);
        String[] record = invoiceToArray(invoice);
        
        if (csvService.updateRecordById(FILENAME, invoiceId, record, 0)) {
            log.info("Invoice updated: {}", invoiceId);
            return invoice;
        }
        return null;
    }

    /**
     * Delete invoice
     */
    public boolean deleteInvoice(String invoiceId) {
        boolean deleted = csvService.deleteRecordById(FILENAME, invoiceId, 0);
        if (deleted) {
            log.info("Invoice deleted: {}", invoiceId);
        }
        return deleted;
    }

    /**
     * Get invoices by patient
     */
    public List<Invoice> getInvoicesByPatient(String patientId) {
        List<Invoice> allInvoices = getAllInvoices();
        List<Invoice> result = new ArrayList<>();
        
        for (Invoice invoice : allInvoices) {
            if (invoice.getPatient() != null && invoice.getPatient().getPatientId() != null
                && invoice.getPatient().getPatientId().equals(patientId)) {
                result.add(invoice);
            }
        }
        return result;
    }

    /**
     * Get invoices by status
     */
    public List<Invoice> getInvoicesByStatus(String status) {
        List<Invoice> allInvoices = getAllInvoices();
        List<Invoice> result = new ArrayList<>();
        
        for (Invoice invoice : allInvoices) {
            if (status != null && status.equalsIgnoreCase(invoice.getStatus())) {
                result.add(invoice);
            }
        }
        return result;
    }

    /**
     * Convert Invoice object to CSV array
     */
    private String[] invoiceToArray(Invoice invoice) {
        String patientId = invoice.getPatient() != null ? invoice.getPatient().getPatientId() : "";
        String date = invoice.getDate() != null ? invoice.getDate().format(DATE_FORMATTER) : "";
        return new String[]{
            invoice.getInvoiceId(),
            patientId,
            invoice.getRegistrationDate(),
            date,
            String.valueOf(invoice.getAmount()),
            invoice.getStatus() != null ? invoice.getStatus() : "",
            invoice.getPaymentMethod() != null ? invoice.getPaymentMethod() : "",
            invoice.getDescription() != null ? invoice.getDescription() : ""
        };
    }

    /**
     * Convert CSV array to Invoice object
     */
    private Invoice arrayToInvoice(String[] record) {
        if (record.length < 8) return null;

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(record[0]);
        if (record[1] != null && !record[1].isEmpty()) {
            Patient p = new Patient();
            p.setPatientId(record[1]);
            invoice.setPatient(p);
        }
        invoice.setRegistrationDate(record[2]);
        invoice.setDate(!record[3].isEmpty() ? LocalDate.parse(record[3], DATE_FORMATTER) : null);
        invoice.setAmount(!record[4].isEmpty() ? Double.parseDouble(record[4]) : 0.0);
        invoice.setStatus(record[5]);
        invoice.setPaymentMethod(record[6]);
        invoice.setDescription(record[7]);

        return invoice;
    }
}
