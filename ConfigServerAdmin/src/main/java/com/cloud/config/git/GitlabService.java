package com.cloud.config.git;

import com.cloud.config.vo.RepoFileVo;
import com.cloud.config.vo.RepoVo;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.cloud.config.utils.GitUtils;
import com.messners.gitlab.api.GitLabApi;
import com.messners.gitlab.api.models.Project;
import com.messners.gitlab.api.models.RepositoryFile;
import com.messners.gitlab.api.models.TreeItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.git
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 14:51
 */
@Service
public class GitlabService {

    private static final Logger log = LoggerFactory.getLogger(GitlabService.class);

    private static final String DEFAULT_BRANCH = "master";

    private static final String DEFAULT_ENCODE = "base64";

    @Resource
    private Environment env;

    @Resource
    private GitLabApi gitLabApi;

    /**
     * 获取所有的配置文件
     *
     * @return
     */
    public List<RepoVo> allConfigs() throws Exception {

        final Project project = gitLabApi.getProjectApi().getProject(env.getProperty("gitlab.group"), env.getProperty("gitlab.repository"));
        List<TreeItem> items = gitLabApi.getRepositoryApi().getTree(project.getId());
        //转换视图
        List<RepoVo> repoVos = Lists.transform(items, new Function<TreeItem, RepoVo>() {
            @Override
            public RepoVo apply(TreeItem treeItem) {
                RepoVo repoVo = new RepoVo();
                repoVo.setId(project.getId());
                repoVo.setBranch(project.getDefaultBranch());
                String name = treeItem.getName();
                repoVo.setName(name);
                repoVo.setPath(treeItem.getName());
                String serviceId = name.split("\\.")[0];
                repoVo.setServiceId(serviceId);
                return repoVo;
            }
        });
        return repoVos;
    }

    /**
     * 获取指定的文件内容
     *
     * @param projectId
     * @param name
     * @param branch
     * @return
     */
    public RepoFileVo fetchFile(Integer projectId, String name, String branch) throws Exception {
        Preconditions.checkNotNull(projectId);
        Preconditions.checkNotNull(name);
        if (StringUtils.isBlank(branch)) {
            branch = DEFAULT_BRANCH;
        }
        RepositoryFile file = gitLabApi.getRepositoryFileApi().getFile(name, projectId, branch);
        RepoFileVo fileVo = new RepoFileVo();
        fileVo.setId(projectId);
        fileVo.setPath(file.getFilePath());
        String fileName = file.getFileName();
        fileVo.setName(fileName);
        fileVo.setBranch(branch);
        String serviceId = fileName.split("\\.")[0];
        fileVo.setServiceId(serviceId);
        String content = GitUtils.decode(file.getContent());
        fileVo.setContent(content);
        return fileVo;
    }

    /**
     * 更新并提交文件内容
     *
     * @param fileVo
     * @return
     */
    public RepoFileVo updateFile(RepoFileVo fileVo) throws Exception {
        RepositoryFile file = new RepositoryFile();
        //进行base64 编码
        String source = GitUtils.encode(fileVo.getContent());
        file.setContent(source);
        file.setEncoding(DEFAULT_ENCODE);
        file.setFilePath(fileVo.getPath());
        RepositoryFile updateFile = gitLabApi.getRepositoryFileApi().updateFile(file, fileVo.getId(), fileVo.getBranch(), fileVo.getCommit());
        RepoFileVo newFile = null;
        if (updateFile != null) {
            newFile = fetchFile(fileVo.getId(), fileVo.getPath(), fileVo.getBranch());
        }
        return newFile;
    }

    /**
     * 创建并提交文件
     *
     * @param fileVo
     * @return
     */
    public RepoFileVo createFile(RepoFileVo fileVo) throws Exception {
        RepositoryFile file = new RepositoryFile();
        //进行base64编码
        String source = GitUtils.encode(fileVo.getContent());
        file.setContent(source);
        file.setFilePath(fileVo.getPath());
        file.setEncoding(DEFAULT_ENCODE);
        RepositoryFile repositoryFile = gitLabApi.getRepositoryFileApi().createFile(file, fileVo.getId(), fileVo.getBranch(), fileVo.getCommit());
        RepoFileVo newRepo = null;
        if (repositoryFile != null) {
            newRepo = fetchFile(fileVo.getId(), fileVo.getName(), fileVo.getBranch());
        }
        return newRepo;
    }
}
