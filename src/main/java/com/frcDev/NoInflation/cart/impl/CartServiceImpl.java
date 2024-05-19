package com.frcDev.NoInflation.cart.impl;

import com.frcDev.NoInflation.cart.Cart;
import com.frcDev.NoInflation.cart.CartRepository;
import com.frcDev.NoInflation.cart.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public void createCart(Cart cart) {
        cartRepository.save(cart);

    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateCart(Long id, Cart updatedCart) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
                cart.setProduct(updatedCart.getProduct());
                cart.setUser(updatedCart.getUser());
                cartRepository.save(cart);
                return true;
        }
        return false;
    }

    @Override
    public boolean deleteCart(Long id) {
        try {
            cartRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
