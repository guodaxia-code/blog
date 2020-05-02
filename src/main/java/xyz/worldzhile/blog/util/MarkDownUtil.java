package xyz.worldzhile.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkDownUtil {

    /** 增加扩展【标题锚点,表格生成】
     *markdown 转换为html
     * @param markDownContent
     * @return
     */
    public static String markDownContentToHtml(String markDownContent){
    //H生成id
        Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
    // 转换table的Html
        List<Extension> tableExtension= Arrays.asList(TablesExtension.create());
        Parser parser= Parser.builder().extensions(tableExtension).build();
        Node document=parser.parse(markDownContent);
        HtmlRenderer renderer=HtmlRenderer.builder().extensions(headingAnchorExtensions).extensions(tableExtension).attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                return new CustomAttributeProvider();
            }
        }).build();

        return renderer.render(document);

    }



    /**
     * 处理标签的属性
     */

    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
//        让a链接 加上target 为_blank
            if (node instanceof Link){
                attributes.put("target","_blank");
            }
            if (node instanceof TableBlock){
                attributes.put("class","ui celled table");
            }
        }

    }


//    基本使用
    public static String markDownContentToHtmlEasy(String markDownContent){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markDownContent);
        HtmlRenderer renderer=HtmlRenderer.builder().build();
        return renderer.render(document);
    }


    //test
    public static void main(String[] args) {
        String result = markDownContentToHtml("**一、首页**\n" +
                "# 1.主页页面\n" +
                "*首页主要是一些文字描述和个人信息，如果你的显示屏分辨率比较高或者页面缩放的比较小的话，可以看到下面有最新推荐专栏*");
    }

}

