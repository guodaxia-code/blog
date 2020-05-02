package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.service.TypeService;
import xyz.worldzhile.blog.util.PageBean;
import xyz.worldzhile.blog.util.UuidUtil;

import java.util.List;

@Controller
@RequestMapping("admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("types/{page}/{pageCount}")
    public String getTypes(@PathVariable Integer page, @PathVariable Integer pageCount, Model model){

        PageBean<Type> typePageBean = new PageBean(page,pageCount);
        List<Type> types = typeService.listType(typePageBean);
        typePageBean.setList(types);
        model.addAttribute("page",typePageBean);

        return  "admin/types";
    }

    @GetMapping("type-input")
    public String getTypeinput(){
        return  "admin/type-input";
    }

    @PostMapping("type-input")
    public String saveTypeinput(Type type, Model model,RedirectAttributes redirectAttributes){

        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName!=null){
            model.addAttribute("message","请不要添加已有的分类");
            return "admin/type-input";
        }

        type.setId(UuidUtil.getUuid());
        Type type1 = typeService.saveType(type);

        if (type1==null){
            redirectAttributes.addAttribute("message","新增失败");
        }else {
            redirectAttributes.addAttribute("message","新增成功");
        }

        return  "redirect:/admin/types/1/6";
    }


    @GetMapping("{id}/type-editor")
    public String getTypeeditor(@PathVariable String id,Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return  "admin/type-input";
    }

    @PostMapping("{id}/type-editor")
    public String getTypeeditor(Type type,@PathVariable String id, Model model,RedirectAttributes redirectAttributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName!=null){
            model.addAttribute("message","请不要添加已有的分类");
            return "admin/type-input";
        }

        Type type1 = typeService.updateType(type);

        if (type1==null){
            redirectAttributes.addAttribute("message","更新失败");
        }else {
            redirectAttributes.addAttribute("message","更新成功");
        }

        return  "redirect:/admin/types/1/6";
    }

    @GetMapping("{id}/type-delete")
    public String getTypedelete(@PathVariable String id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addAttribute("message","删除成功");
        return  "redirect:/admin/types/1/6";
    }

}
