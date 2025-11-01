package co.edu.umanizales.hospital.controller;

import co.edu.umanizales.hospital.model.Invoice;
import co.edu.umanizales.hospital.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(invoice));
        } catch (Exception e) {
            log.error("Error creating invoice", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String invoiceId) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            return invoice != null ? ResponseEntity.ok(invoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving invoice", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        try {
            return ResponseEntity.ok(invoiceService.getAllInvoices());
        } catch (Exception e) {
            log.error("Error retrieving all invoices", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable String invoiceId, @RequestBody Invoice invoice) {
        try {
            Invoice updated = invoiceService.updateInvoice(invoiceId, invoice);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating invoice", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String invoiceId) {
        try {
            return invoiceService.deleteInvoice(invoiceId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting invoice", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Invoice>> getInvoicesByPatient(@PathVariable String patientId) {
        try {
            return ResponseEntity.ok(invoiceService.getInvoicesByPatient(patientId));
        } catch (Exception e) {
            log.error("Error retrieving invoices by patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(invoiceService.getInvoicesByStatus(Invoice.InvoiceStatus.valueOf(status)));
        } catch (Exception e) {
            log.error("Error retrieving invoices by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
