package com.frcDev.NoInflation.shop;

import java.util.List;

public interface ShopService {
    List<Shop> getAllShops();

    void createShop(Shop shop);

    Shop getShopById(Long id);

    boolean updateShop(Shop shop, Long id);

    boolean deleteShopById(Long id);


}
