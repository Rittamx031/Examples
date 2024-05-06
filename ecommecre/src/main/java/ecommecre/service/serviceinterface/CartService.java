package ecommecre.service.serviceinterface;

import ecommecre.model.DTO.CartDto;

public interface CartService {
    public CartDto updateCart(CartDto cart, Integer productId, Integer quantity, boolean isReplace);

    public Integer getTotalQuantity(CartDto cart);

    public Double getTotalPrice(CartDto cart);

    public CartDto save(CartDto cart);
}
