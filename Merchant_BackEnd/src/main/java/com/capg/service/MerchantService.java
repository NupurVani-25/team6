package com.capg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.bean.AddProduct;
import com.capg.repo.IMerchantRepo;

@Service
public class MerchantService implements IMerchantService {

	@Autowired
	IMerchantRepo repo;

	@Override
	public boolean addProduct(AddProduct product) {

		boolean res = false;
		if (!(repo.existsById(product.getProductId()))) {
			repo.save(product);
			res = true;
		}
		return res;

	}

	@Override
	public void removeProduct(String id, String productName, String quantity) throws Exception {
		try {

			AddProduct bean = repo.findById(id).get();
			if (bean != null) {
				int qty = Integer.parseInt(bean.getQuantity()) - Integer.parseInt(quantity);
				if (qty > 0) {
					bean.setQuantity(String.valueOf(qty));
					repo.save(bean);

				} else if (qty == 0) {
					repo.deleteById(id);
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public String searchProduct(String id, String quantity) throws Exception {
		String res = null;
		try {
			AddProduct bean = repo.findById(id).get();
			if (bean != null) {
				int qty = Integer.parseInt(bean.getQuantity()) - Integer.parseInt(quantity);
				if (qty > 0) {
					res = "updated";
				} else if (qty == 0) {
					res = "deleted";
				} else {
					res = "notUpdated";
				}
			}
		} catch (Exception e) {
			res = "notFound";
		}
		return res;
	}

}
