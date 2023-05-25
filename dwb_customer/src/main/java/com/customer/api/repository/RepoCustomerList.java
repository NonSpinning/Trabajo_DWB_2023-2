package com.customer.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.api.dto.DtoCustomerList;

public interface RepoCustomerList extends JpaRepository<DtoCustomerList, Integer>{

	List<DtoCustomerList> findByStatus(Integer status);

}
