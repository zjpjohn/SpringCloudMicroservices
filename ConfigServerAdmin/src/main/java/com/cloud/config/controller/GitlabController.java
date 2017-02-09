package com.cloud.config.controller;

import com.cloud.config.remote.ConfigServerService;
import com.cloud.config.vo.RepoFileVo;
import com.cloud.config.vo.RepoVo;
import com.cloud.config.git.GitlabService;
import com.cloud.config.vo.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.controller
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 14:55
 */
@Controller
@RequestMapping("/config")
public class GitlabController {

    private static final Logger log = LoggerFactory.getLogger(GitlabController.class);

    @Resource
    private GitlabService gitlabService;

    @Resource
    private ConfigServerService configServerService;

    @Resource
    private Environment env;


    @RequestMapping("/")
    public String home() {
        return "redirect:index";
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("id", env.getProperty("gitlab.projectId"));
        view.addObject("branch", env.getProperty("gitlab.branch"));
        return view;
    }


    /**
     * 获取所有配置文件信息
     *
     * @return
     */
    @RequestMapping("/all")
    public ModelAndView allConfigList() {
        ModelAndView view = new ModelAndView("all");
        try {
            List<RepoVo> configs = gitlabService.allConfigs();
            view.addObject("status", 1)
                    .addObject("configs", configs);
        } catch (Exception e) {
            log.error("get all configs error:{}", e);
            view.addObject("status", 0)
                    .addObject("error", "get all config error");
        }
        return view;
    }

    /**
     * 获取指定文件的内容信息
     *
     * @param projectId project
     * @param path      file path
     * @param branch    分支
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView fileInfo(@RequestParam("id") Integer projectId,
                                 @RequestParam("path") String path,
                                 @RequestParam("branch") String branch) {
        ModelAndView view = new ModelAndView("info");
        try {
            RepoFileVo fileVo = gitlabService.fetchFile(projectId, path, branch);
            view.addObject("status", 1)
                    .addObject("info", fileVo);
        } catch (Exception e) {
            log.error("get file info error:{}", e);
            view.addObject("status", 0)
                    .addObject("error", "get file info error");
        }
        return view;
    }

    /**
     * 跳转更新配置文件页面
     *
     * @param projectId
     * @param path
     * @param branch
     * @return
     */
    @RequestMapping("/updatePage")
    public ModelAndView redirectUpdate(@RequestParam("id") Integer projectId,
                                       @RequestParam("path") String path,
                                       @RequestParam("branch") String branch) {
        ModelAndView view = new ModelAndView("update");
        try {
            RepoFileVo fileVo = gitlabService.fetchFile(projectId, path, branch);
            view.addObject("status", 1)
                    .addObject("info", fileVo);
        } catch (Exception e) {
            log.error("get update file info error:{}", e);
            view.addObject("status", 0)
                    .addObject("error", "get file info error");
        }
        return view;
    }

    /**
     * 更新配置信息
     *
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonData updateInfo(RepoFileVo fileVo) {
        JsonData data = null;
        try {
            RepoFileVo repoFileVo = gitlabService.updateFile(fileVo);
            if (repoFileVo != null) {
                String name = fileVo.getName();
                //刷新bus
                String destination = name.split("\\.")[0];
                data = configServerService.refresh(destination);
                return data;
            }
        } catch (Exception e) {
            log.error("update file info error:{}", e);
        }
        return JsonData.failure();
    }

    /**
     * 跳转创建配置文件页面
     *
     * @param projectId
     * @param branch
     * @return
     */
    @RequestMapping("/createPage")
    public ModelAndView createRedirect(@RequestParam("id") Integer projectId,
                                       @RequestParam("branch") String branch) {
        ModelAndView view = new ModelAndView("create");
        view.addObject("id", projectId)
                .addObject("branch", branch);
        return view;
    }

    /**
     * 创建配置文件
     *
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonData createInfo(RepoFileVo fileVo) {
        try {
            //文件路径就是文件名称，因为文件放在根路径
            fileVo.setPath(fileVo.getName());
            RepoFileVo repoFileVo = gitlabService.createFile(fileVo);
            return JsonData.success();
        } catch (Exception e) {
            log.error("create file error:{}", e);
        }
        return JsonData.failure();
    }

    /**
     * 刷新配置文件页面
     *
     * @return
     */
    @RequestMapping("/refreshPage")
    public ModelAndView refreshPage() {
        ModelAndView view = new ModelAndView("refresh");
        try {
            List<RepoVo> repoVos = gitlabService.allConfigs();
            view.addObject("repoVos", repoVos)
                    .addObject("status", 1);
        } catch (Exception e) {
            log.error("get refresh configs error:{}", e);
            view.addObject("error", "get refresh configs error")
                    .addObject("status", 0);
        }
        return view;
    }

    /**
     * 刷新配置文件
     *
     * @param destination 指定刷新实例
     * @return
     */
    @RequestMapping("/refresh")
    @ResponseBody
    public JsonData refresh(@RequestParam(value = "dest", required = false) String destination) {
        JsonData data = null;
        try {
            if (StringUtils.isBlank(destination)) {
                //刷新指定文件
                data = configServerService.refresh(destination);
            } else {
                //全部刷新
                data = configServerService.refresh(null);
            }
            return data;
        } catch (Exception e) {
            log.error("refresh config error:{}", e);
        }
        return JsonData.failure();
    }
}
