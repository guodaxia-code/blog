package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.service.TagService;
import xyz.worldzhile.blog.util.PageBean;
import xyz.worldzhile.blog.util.UuidUtil;

import java.util.List;

@Controller
@RequestMapping("admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("tags/{page}/{pageCount}")
    public String getTags(@PathVariable Integer page, @PathVariable Integer pageCount, Model model){

        PageBean<Tag> pageBean = new PageBean(page,pageCount);
        List<Tag> tags = tagService.listTag(pageBean);
        pageBean.setList(tags);
        model.addAttribute("page",pageBean);
        return  "admin/tags";
    }

    @GetMapping("tag-input")
    public String getTypeinput(){
        return  "admin/tag-input";
    }

    @PostMapping("tag-input")
    public String saveTaginput(Tag tag, Model model, RedirectAttributes redirectAttributes){

        Tag typeByName = tagService.getTagByName(tag.getName());
        if (typeByName!=null){
            model.addAttribute("message","请不要添加已有的分类");
            return "admin/tag-input";
        }

        tag.setId(UuidUtil.getUuid());
        Tag tag1 = tagService.saveTag(tag);

        if (tag1==null){
            redirectAttributes.addAttribute("message","新增失败");
        }else {
            redirectAttributes.addAttribute("message","新增成功");
        }

        return  "redirect:/admin/tags/1/6";
    }


       @GetMapping("{id}/tag-editor")
    public String getTageditor(@PathVariable String id,Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return  "admin/tag-input";
    }

    @PostMapping("{id}/tag-editor")
    public String getTageditor(Tag tag,@PathVariable String id, Model model,RedirectAttributes redirectAttributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName!=null){
            model.addAttribute("message","请不要添加已有的标签");
            return "admin/tag-input";
        }

        Tag tag1 = tagService.updateTag(tag);

        if (tag1==null){
            redirectAttributes.addAttribute("message","更新失败");
        }else {
            redirectAttributes.addAttribute("message","更新成功");
        }

        return  "redirect:/admin/tags/1/6";
    }

    @GetMapping("{id}/tag-delete")
    public String getTagdelete(@PathVariable String id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addAttribute("message","删除成功");
        return  "redirect:/admin/tags/1/6";
    }


}
