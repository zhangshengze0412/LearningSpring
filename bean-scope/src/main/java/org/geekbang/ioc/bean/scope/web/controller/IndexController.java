package org.geekbang.ioc.bean.scope.web.controller;

import org.geekbang.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @className: IndexController
 * @description: 首页
 * @author: ZSZ
 * @date: 2021/2/22 16:12
 */
@Controller
public class IndexController {

    @Autowired
    private User user;

    @GetMapping("/index")
     public String index(Model model){
        // userObject -> 渲染上下文
        // user 对象存在 ServletContext，上下文名称：scopedTarget.user == 新生成 Bean 名称
        model.addAttribute("userObject", user);
         return "index";
     }
}
