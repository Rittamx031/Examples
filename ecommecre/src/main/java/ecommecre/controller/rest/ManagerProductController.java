package ecommecre.controller.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ecommecre.exeption.rest.ApiError;
import ecommecre.model.entity.Brand;
import ecommecre.model.entity.Category;
import ecommecre.model.entity.ImageProduct;
import ecommecre.model.entity.Material;
import ecommecre.model.entity.Origin;
import ecommecre.model.entity.PatternType;
import ecommecre.model.entity.Product;
import ecommecre.model.entity.ProductDetail;
import ecommecre.model.entity.Size;
import ecommecre.model.entity.Styles;
import ecommecre.model.request.ProductAddRequest;
import ecommecre.model.request.ProductDetailRequest;
import ecommecre.model.request.ProductRequest;
import ecommecre.model.request.UpdateProductDetail;
import ecommecre.service.BrandService;
import ecommecre.service.CategoryService;
import ecommecre.service.ManagerProductService;
import ecommecre.service.MaterialService;
import ecommecre.service.OriginService;
import ecommecre.service.PatternTypeService;
import ecommecre.service.ProductDetailService;
import ecommecre.service.ProductService;
import ecommecre.service.SizeService;
import ecommecre.service.StylesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RequestMapping("/admin/managerproduct")
@RestController("restManagerProductController")
public class ManagerProductController {
  @Autowired
  private ManagerProductService service;
  @Autowired
  ProductDetailService service2;
  @Autowired
  private BrandService brandService;

  @Autowired
  private MaterialService materialService;

  @Autowired
  private OriginService originService;

  @Autowired
  private PatternTypeService patternService;

  @Autowired
  private SizeService sizeService;

  @Autowired
  private StylesService stylesService;

  @Autowired
  private CategoryService categoryService;
  @Autowired
  private ProductService productService;

  @PostMapping("saveproduct")
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductAddRequest request) {
    List<String> validationErrors = request.validateError();
    if (!validationErrors.isEmpty()) {
      ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ApiError(HttpStatus.BAD_REQUEST, "Lỗi khi tạo Product", validationErrors));
    }
    return ResponseEntity.ok(service.saveProduct(request));
  }

  @PostMapping("addbrand")
  public Brand addNewBrand(@RequestParam("name") String name) {
    Brand entity = new Brand();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return brandService.add(entity);
  }

  @PostMapping("addmaterial")
  public Material addNewMaterial(@RequestParam("name") String name) {
    Material entity = new Material();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return materialService.add(entity);
  }

  @PostMapping("addorigin")
  public Origin addNewOrigin(@RequestParam("name") String name) {
    Origin entity = new Origin();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return originService.add(entity);
  }

  @PostMapping("addpattern")
  public PatternType addNewPattern(@RequestParam("name") String name) {
    PatternType entity = new PatternType();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return patternService.add(entity);
  }

  @PostMapping("addsize")
  public Size addNewSize(@RequestParam("name") String name) {
    Size entity = new Size();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return sizeService.add(entity);
  }

  @PostMapping("addstyles")
  public Styles addNewStyles(@RequestParam("name") String name) {
    Styles entity = new Styles();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return stylesService.add(entity);
  }

  @PostMapping("addcategory")
  public Category addNewCategory(@RequestParam("name") String name) {
    Category entity = new Category();
    entity.setName(name);
    entity.setStatus(1);
    entity.setDeleted(false);
    return categoryService.add(entity);
  }

  @GetMapping("brand")
  public List<Brand> getBrandList() {
    return brandService.getBrandList();
  }

  @GetMapping("material")
  public List<Material> getMaterialList() {
    return materialService.getMaterialList();
  }

  @GetMapping("origin")
  public List<Origin> getOriginList() {
    return originService.getOriginList();
  }

  @GetMapping("pattern")
  public List<PatternType> getPatternTypeList() {
    return patternService.getPatternTypeList();
  }

  @GetMapping("size")
  public List<Size> getSizeList() {
    return sizeService.getSizeList();
  }

  @GetMapping("styles")
  public List<Styles> getStylesList() {
    return stylesService.getStylesList();
  }

  @GetMapping("category")
  public List<Category> getCategoryList() {
    return categoryService.getCategoryList();
  }

  @GetMapping("/product/get/{idproduct}")
  public ResponseEntity<Product> getProduct(@PathVariable("idproduct") int idproduct) {
    return ResponseEntity.ok().body(productService.getProduct(idproduct));
  }

  @PostMapping("/update/product")
  public ProductDetail updateProductDetail(@RequestBody UpdateProductDetail request) {
    return service.updateProductDetails(request);
  }

  @PostMapping("/update/product/{id}/saveimage")
  public String saveimage(@PathVariable("id") int id, @RequestParam("image") MultipartFile image) {
    return service.saveImage(id, image);
  }

  @PostMapping("/productdetail/save")
  public ProductDetail createProductDetail(@Valid @RequestBody ProductDetailRequest request) throws IOException {
    // System.out.println(request);
    return service2.saveProductDetail(request);
  }

  @PostMapping("/product/save")
  public Product createProductDetail(@Valid @RequestBody ProductRequest request) {
    return service.updateProduct(request);
  }

  @PostMapping("/product/{id}/image/save")
  public ImageProduct uploadImageProduct(@PathVariable("id") int id, @RequestParam("image") MultipartFile image) {
    return service.saveImageProduct(id, image);
  }

  @PostMapping(value = "product/image/productdetails")
  public String postMethodName(
      @RequestParam("file") MultipartFile file,
      @RequestParam("idhoavan") int idhoavan,
      @RequestParam("idproduct") int idproduct) {
    return service.saveImage(idproduct, idhoavan, file);
  }
}