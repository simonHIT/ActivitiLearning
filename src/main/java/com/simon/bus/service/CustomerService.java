package com.simon.bus.service;

import java.util.List;

import com.simon.bus.domain.Customer;
import com.simon.bus.vo.CustomerVo;
import com.simon.sys.utils.DataGridView;

public interface CustomerService {
	List<Customer> queryAllCustomer(CustomerVo customerVo);

	DataGridView loadCustomers(CustomerVo customerVo);

	void addCustomer(CustomerVo customerVo);

	Customer queryCustomerById(Integer id);

	void updateCustomer(CustomerVo customerVo);

	void deleteCustomer(CustomerVo customerVo);

}
