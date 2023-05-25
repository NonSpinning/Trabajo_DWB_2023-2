package com.invoice.api.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoProduct;
import com.invoice.api.entity.Cart;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.api.repository.RepoItem;
import com.invoice.configuration.client.ProductClient;
import com.invoice.exception.ApiException;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	@Autowired
	RepoInvoice repo;
	
	@Autowired
	RepoItem repoItem;

	@Autowired 
	SvcCart svcCart;

	@Autowired
	ProductClient productCl;

	@Override
	public List<Invoice> getInvoices(String rfc) {
		return repo.findByRfcAndStatus(rfc, 1);
	}

	@Override
	public List<Item> getInvoiceItems(Integer invoice_id) {
		return repoItem.getInvoiceItems(invoice_id);
	}

	@Override
	public ApiResponse generateInvoice(String rfc) {
		/*
		 * Requerimiento 5
		 * Implementar el método para generar una factura 
		 */
		List<Cart> products = svcCart.getCart(rfc);
		if(products.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "cart has no items");
		
		Invoice invoice = new Invoice();
		ResponseEntity<DtoProduct> resProd;
		DtoProduct product;
		List<Item> items = new LinkedList<Item>();
		
		invoice.setRfc(rfc);
		invoice.setCreated_at(LocalDateTime.now());
		invoice.setStatus(1);
		Double runningTotal = 0.0;
		
		for (Cart x: products){
			resProd = productCl.getProduct(x.getGtin());
			product = resProd.getBody();

			String gtin = product.getGtin();
			Integer quantity = x.getQuantity();
			Double price = product.getPrice();
			Double total = quantity * price;
			Double taxes = total * .16;
			Double subtotal = total - taxes;

			Item item = new Item();
			item.setGtin(gtin);
			item.setQuantity(quantity);
			item.setUnit_price(price);
			item.setTotal(total);
			item.setTaxes(taxes);
			item.setSubtotal(subtotal);
			item.setStatus(1);
			items.add(item);

			productCl.updateProductStock(gtin, quantity);
			runningTotal += total;
		}

		invoice.setTotal(runningTotal);
		Double taxes = runningTotal * .16;
		invoice.setTaxes(taxes);
		Double subtotal = runningTotal - taxes;
		invoice.setSubtotal(subtotal);

		repo.save(invoice);
		Integer id = invoice.getInvoice_id();
		for (Item x: items){
			x.setId_invoice(id);
			repoItem.save(x);
		}
		svcCart.clearCart(rfc);	
		return new ApiResponse("invoice generated");	
	}
}
