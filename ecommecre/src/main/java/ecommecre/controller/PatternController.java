package ecommecre.controller;

import java.time.LocalDateTime;

import ecommecre.utils.convert.TrangThaiConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ecommecre.model.entity.PatternType;
import ecommecre.service.PatternTypeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/pattern")
public class PatternController {
    @Autowired
    private PatternTypeService patternTypeService;
    private int currentProductCode = 1;
    @Autowired
    TrangThaiConvert convert;

    @ModelAttribute("convert")
    public TrangThaiConvert convert() {
        return convert;
    }
    @GetMapping("/dsPattern")
    public String hienThi(Model model, @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<PatternType> brandPage = patternTypeService.findAll(pageable);
        model.addAttribute("totalPage", brandPage.getTotalPages());
        model.addAttribute("brandPage", brandPage.getContent());
        return "admin/pages/patterntype/hien-thi";
    }

    @GetMapping("/search")
    public String searchByKeyWork(Model model,@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name="keyword",required = false) String keyword) {

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<PatternType> brandPage;
        if (keyword != null && !keyword.isEmpty()) {
            // Nếu từ khóa không rỗng, thực hiện tìm kiếm theo từ khóa
            brandPage = patternTypeService.searchPatternByKeyword(keyword, pageable);
        } else {
            // Nếu không có từ khóa, lấy tất cả thương hiệu
            brandPage = patternTypeService.findAll(pageable);
        }

        model.addAttribute("totalPage", brandPage.getTotalPages());
        model.addAttribute("brandPage", brandPage.getContent());
        model.addAttribute("keyword", keyword); // Truyền từ khóa để hiển thị lại trên giao diện
        return "admin/pages/patterntype/hien-thi";
    }



    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        return "admin/pages/brand/create-brand";
    }

    @GetMapping("/view-update/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("brand",patternTypeService.getById(id));
        return "admin/pages/patterntype/update-pattern";
    }

    @PostMapping("/update/{id}")
    public String update(Model model, @Valid PatternType b, @PathVariable Integer id) {
        b.setUpdatedAt(LocalDateTime.now());
        patternTypeService.update(id, b);
        return "redirect:/admin/pattern/dsPattern";
    }

    @PostMapping("/add")
    public String add(Model model,@Valid PatternType b, BindingResult result) {

        b.setCreatedAt(LocalDateTime.now());
        b.setUpdatedAt(LocalDateTime.now());
        b.setStatus(1);
        currentProductCode++;
        patternTypeService.add(b);
        return "redirect:/admin/pattern/dsPattern";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") Integer id) {
        patternTypeService.deletePattern(id);
        return "redirect:/admin/pattern/dsPattern";
    }
}
