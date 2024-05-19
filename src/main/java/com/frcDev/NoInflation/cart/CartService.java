package com.frcDev.NoInflation.cart;

import java.util.List;

public interface CartService {

    List<Cart> findAll();

    void createCart(Cart cart);

    Cart getCartById(Long id);

    boolean updateCart(Long id, Cart updatedCart);

    boolean deleteCart(Long id);
}
