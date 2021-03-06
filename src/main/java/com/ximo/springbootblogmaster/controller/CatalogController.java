package com.ximo.springbootblogmaster.controller;

import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.handler.ConstraintViolationExceptionHandler;
import com.ximo.springbootblogmaster.service.CatalogService;
import com.ximo.springbootblogmaster.util.AuthenticationUtil;
import com.ximo.springbootblogmaster.vo.CatalogVO;
import com.ximo.springbootblogmaster.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类控制器.
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 获取分类列表
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        // 判断操作用户是否是分类的所有者
        boolean isOwner = AuthenticationUtil.isOwner(username);

        model.addAttribute("isCatalogsOwner", isOwner);
        model.addAttribute("catalogs", catalogs);
        return "/userspace/u :: #catalogRepleace";
    }

    /**
     * 发表分类
     * 指定用户才能操作方法
     *
     * @param catalogVO
     * @return
     */
    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")
    public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {

        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除分类
     *
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> delete(@RequestParam("username") String username, @PathVariable("id") Long id) {
        try {
            catalogService.removeCatalog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 获取分类编辑界面
     *
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String getCatalogEdit(Model model) {
        Catalog catalog = new Catalog();
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }

    /**
     * 根据 Id 获取分类信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String getCatalogById(@PathVariable("id") Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog", catalog);
        return "/userspace/catalogedit";
    }

}
