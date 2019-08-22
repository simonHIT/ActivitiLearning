package com.simon.bus.service;

import java.util.List;

import com.simon.bus.domain.Provider;
import com.simon.bus.vo.ProviderVo;
import com.simon.sys.utils.DataGridView;

public interface ProviderService {
	List<Provider> queryAllProvider(ProviderVo providerVo);

	DataGridView loadProviders(ProviderVo providerVo);

	void addProvider(ProviderVo providerVo);

	Provider queryProviderById(Integer id);

	void updateProvider(ProviderVo providerVo);

	void deleteProvider(ProviderVo providerVo);

}
