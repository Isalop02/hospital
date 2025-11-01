package co.edu.umanizales.hospital.service;

import co.edu.umanizales.hospital.model.Invoice;
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
        invoice.setInvoiceDate(LocalDate.now().format(DATE_FORMATTER));
        
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
            if (invoice.getPatientId().equals(patientId)) {
                result.add(invoice);
            }
        }
        return result;
    }

    /**
     * Get invoices by status
     */
    public List<Invoice> getInvoicesByStatus(Invoice.InvoiceStatus status) {
        List<Invoice> allInvoices = getAllInvoices();
        List<Invoice> result = new ArrayList<>();
        
        for (Invoice invoice : allInvoices) {
            if (invoice.getStatus() == status) {
                result.add(invoice);
            }
        }
        return result;
    }

    /**
     * Convert Invoice object to CSV array
     */
    private String[] invoiceToArray(Invoice invoice) {
        return new String[]{
            invoice.getInvoiceId(),
            invoice.getPatientId(),
            invoice.getRegistrationDate(),
            invoice.getInvoiceDate(),
            String.valueOf(invoice.getSubtotal()),
            String.valueOf(invoice.getTax()),
            String.valueOf(invoice.getTotal()),
            invoice.getStatus() != null ? invoice.getStatus().toString() : "",
            invoice.getDescription(),
            invoice.getItemIds()
        };
    }

    /**
     * Convert CSV array to Invoice object
     */
    private Invoice arrayToInvoice(String[] record) {
        if (record.length < 10) return null;

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(record[0]);
        invoice.setPatientId(record[1]);
        invoice.setRegistrationDate(record[2]);
        invoice.setInvoiceDate(record[3]);
        invoice.setSubtotal(!record[4].isEmpty() ? Double.parseDouble(record[4]) : 0.0);
        invoice.setTax(!record[5].isEmpty() ? Double.parseDouble(record[5]) : 0.0);
        invoice.setTotal(!record[6].isEmpty() ? Double.parseDouble(record[6]) : 0.0);
        invoice.setStatus(!record[7].isEmpty() ? Invoice.InvoiceStatus.valueOf(record[7]) : null);
        invoice.setDescription(record[8]);
        invoice.setItemIds(record[9]);

        return invoice;
    }
}
