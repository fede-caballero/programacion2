package com.frcDev.NoInflation.shop.impl;

import com.frcDev.NoInflation.shop.Shop;
import com.frcDev.NoInflation.shop.ShopRepository;
import com.frcDev.NoInflation.shop.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }
    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public boolean updateShop(Shop updatedShop, Long id) {
        Optional<Shop> shopOptional = shopRepository.findById(id);
        if (shopOptional.isPresent()) {
            Shop shopToUpdate = shopOptional.get();
            shopToUpdate.setShopName(updatedShop.getShopName());
            shopToUpdate.setDescription(updatedShop.getDescription());
            shopRepository.save(shopToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteShopById(Long id) {
        try {
            shopRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void createShop(Shop shop) {
        shopRepository.save(shop);

    }

    @Override
    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

}
