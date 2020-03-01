package com.dz.novel.controller;


import com.dz.novel.component.NovelScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Deng Zhou
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelScheduled novelScheduled;

    @RequestMapping("/zxGetOneCol")
    public void zxGetOneCol() {
        novelScheduled.zxGetOneCol();
    }


    @RequestMapping("/zxGetAll")
    public void zxGetAll() {
        novelScheduled.zxGetAll();
    }

    @RequestMapping("/zxFileFailRetry")
    public void zxFileFailRetry() {
        novelScheduled.zxFileFailRetry();
    }

    @RequestMapping("/zxLikeFailRetry")
    public void zxLikeFailRetry() {
        novelScheduled.zxLikeFailRetry();
    }

    @RequestMapping("/refreshLike")
    public void refreshLike() {
        novelScheduled.refreshLike();
    }

    @RequestMapping("/test")
    public void test() {
        novelScheduled.test();
    }


}

