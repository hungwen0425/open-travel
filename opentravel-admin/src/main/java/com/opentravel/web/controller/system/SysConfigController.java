package com.opentravel.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.opentravel.common.annotation.Log;
import com.opentravel.common.core.controller.BaseController;
import com.opentravel.common.core.domain.AjaxResult;
import com.opentravel.common.core.page.TableDataInfo;
import com.opentravel.common.enums.BusinessType;
import com.opentravel.common.utils.poi.ExcelUtil;
import com.opentravel.system.domain.SysConfig;
import com.opentravel.system.service.ISysConfigService;

/**
 * 參數配置 信息操作處理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController
{
    private String prefix = "system/config";

    @Autowired
    private ISysConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String config()
    {
        return prefix + "/config";
    }

    /**
     * 查詢參數配置列表
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysConfig config)
    {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "參數管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysConfig config)
    {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        return util.exportExcel(list, "參數數據");
    }

    /**
     * 新增參數配置
     */
    @RequiresPermissions("system:config:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存參數配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "參數管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysConfig config)
    {
        if (!configService.checkConfigKeyUnique(config))
        {
            return error("新增參數'" + config.getConfigName() + "'失敗，參數鍵名已存在");
        }
        config.setCreateBy(getLoginName());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改參數配置
     */
    @RequiresPermissions("system:config:edit")
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap)
    {
        mmap.put("config", configService.selectConfigById(configId));
        return prefix + "/edit";
    }

    /**
     * 修改保存參數配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "參數管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysConfig config)
    {
        if (!configService.checkConfigKeyUnique(config))
        {
            return error("修改參數'" + config.getConfigName() + "'失敗，參數鍵名已存在");
        }
        config.setUpdateBy(getLoginName());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 刪除參數配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "參數管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        configService.deleteConfigByIds(ids);
        return success();
    }

    /**
     * 刷新參數緩存
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "參數管理", businessType = BusinessType.CLEAN)
    @GetMapping("/refreshCache")
    @ResponseBody
    public AjaxResult refreshCache()
    {
        configService.resetConfigCache();
        return success();
    }

    /**
     * 校驗參數鍵名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public boolean checkConfigKeyUnique(SysConfig config)
    {
        return configService.checkConfigKeyUnique(config);
    }
}
