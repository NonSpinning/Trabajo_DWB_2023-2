package com.invoice.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoice.api.entity.Invoice;

public interface RepoInvoice extends JpaRepository<Invoice, Integer>{

	List<Invoice> findByRfcAndStatus(String rfc, Integer status);

}
