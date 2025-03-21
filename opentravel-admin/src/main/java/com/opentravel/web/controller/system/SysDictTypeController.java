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
import com.opentravel.common.core.domain.Ztree;
import com.opentravel.common.core.domain.entity.SysDictType;
import com.opentravel.common.core.page.TableDataInfo;
import com.opentravel.common.enums.BusinessType;
import com.opentravel.common.utils.poi.ExcelUtil;
import com.opentravel.system.service.ISysDictTypeService;

/**
 * 數據字典信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController
{
    private String prefix = "system/dict/type";

    @Autowired
    private ISysDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType()
    {
        return prefix + "/type";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(SysDictType dictType)
    {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @Log(title = "字典類型", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDictType dictType)
    {

        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        return util.exportExcel(list, "字典類型");
    }

    /**
     * 新增字典類型
     */
    @RequiresPermissions("system:dict:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存字典類型
     */
    @Log(title = "字典類型", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDictType dict)
    {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("新增字典'" + dict.getDictName() + "'失敗，字典類型已存在");
        }
        dict.setCreateBy(getLoginName());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典類型
     */
    @RequiresPermissions("system:dict:edit")
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap)
    {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典類型
     */
    @Log(title = "字典類型", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDictType dict)
    {
        if (!dictTypeService.checkDictTypeUnique(dict))
        {
            return error("修改字典'" + dict.getDictName() + "'失敗，字典類型已存在");
        }
        dict.setUpdateBy(getLoginName());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    @Log(title = "字典類型", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        dictTypeService.deleteDictTypeByIds(ids);
        return success();
    }

    /**
     * 刷新字典緩存
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典類型", businessType = BusinessType.CLEAN)
    @GetMapping("/refreshCache")
    @ResponseBody
    public AjaxResult refreshCache()
    {
        dictTypeService.resetDictCache();
        return success();
    }

    /**
     * 查詢字典詳細
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap)
    {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "system/dict/data/data";
    }

    /**
     * 校驗字典類型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public boolean checkDictTypeUnique(SysDictType dictType)
    {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * 選擇字典樹
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType, ModelMap mmap)
    {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return prefix + "/tree";
    }

    /**
     * 加載字典列表樹
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = dictTypeService.selectDictTree(new SysDictType());
        return ztrees;
    }
}
