package ecommecre.repository;

import ecommecre.model.entity.CartDetail;


import java.util.Collection;

public interface IShoppingCartService {

    void add(CartDetail item);

    void remove(int id);

    CartDetail update(int id, int qty);

    void clear();

    Collection<CartDetail> getAllItems();

    int getCount();

    double getAmount();
}
