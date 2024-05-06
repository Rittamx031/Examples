package ecommecre.model.cookieentity;

import ecommecre.model.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
  ProductDetail productDetaill;
  int quantity;
}
