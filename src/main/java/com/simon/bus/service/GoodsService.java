package com.simon.bus.service;

import java.util.List;

import com.simon.bus.domain.Goods;
import com.simon.bus.vo.GoodsVo;
import com.simon.sys.utils.DataGridView;

public interface GoodsService {

	List<Goods> queryAllGoods(GoodsVo goodsVo);

	DataGridView loadGoods(GoodsVo goodsVo);

	void addGoods(GoodsVo goodsVo);

	Goods queryGoodsById(Integer id);

	void updateGoods(GoodsVo goodsVo);

	void deleteGoods(GoodsVo goodsVo);

	List<Goods> loadGoodsByProviderId(GoodsVo goodsVo);


}
