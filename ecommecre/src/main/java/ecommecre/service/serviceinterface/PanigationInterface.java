package ecommecre.service.serviceinterface;

import java.util.List;

public interface PanigationInterface<T> {
  List<T> getPageNo(int pageNo, int pageSize, String sortBy, boolean sortDir);
  int getPageNumber(int rowcount);
  int[] getPanigation(int rowcount, int pageno);
}
