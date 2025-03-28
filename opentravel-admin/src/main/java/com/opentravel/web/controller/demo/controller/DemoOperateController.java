package com.opentravel.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.opentravel.common.core.controller.BaseController;
import com.opentravel.common.core.domain.AjaxResult;
import com.opentravel.common.core.page.PageDomain;
import com.opentravel.common.core.page.TableDataInfo;
import com.opentravel.common.core.page.TableSupport;
import com.opentravel.common.core.text.Convert;
import com.opentravel.common.exception.ServiceException;
import com.opentravel.common.utils.StringUtils;
import com.opentravel.common.utils.poi.ExcelUtil;
import com.opentravel.web.controller.demo.domain.CustomerModel;
import com.opentravel.web.controller.demo.domain.UserOperateModel;

/**
 * 操作控制
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/operate")
public class DemoOperateController extends BaseController
{
    private String prefix = "demo/operate";

    private final static Map<Integer, UserOperateModel> users = new LinkedHashMap<Integer, UserOperateModel>();
    {
        users.put(1, new UserOperateModel(1, "1000001", "測試1", "0", "15888888888", "ry@qq.com", 150.0, "0"));
        users.put(2, new UserOperateModel(2, "1000002", "測試2", "1", "15666666666", "ry@qq.com", 180.0, "1"));
        users.put(3, new UserOperateModel(3, "1000003", "測試3", "0", "15666666666", "ry@qq.com", 110.0, "1"));
        users.put(4, new UserOperateModel(4, "1000004", "測試4", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.put(5, new UserOperateModel(5, "1000005", "測試5", "0", "15666666666", "ry@qq.com", 140.0, "1"));
        users.put(6, new UserOperateModel(6, "1000006", "測試6", "1", "15666666666", "ry@qq.com", 330.0, "1"));
        users.put(7, new UserOperateModel(7, "1000007", "測試7", "0", "15666666666", "ry@qq.com", 160.0, "1"));
        users.put(8, new UserOperateModel(8, "1000008", "測試8", "1", "15666666666", "ry@qq.com", 170.0, "1"));
        users.put(9, new UserOperateModel(9, "1000009", "測試9", "0", "15666666666", "ry@qq.com", 180.0, "1"));
        users.put(10, new UserOperateModel(10, "1000010", "測試10", "0", "15666666666", "ry@qq.com", 210.0, "1"));
        users.put(11, new UserOperateModel(11, "1000011", "測試11", "1", "15666666666", "ry@qq.com", 110.0, "1"));
        users.put(12, new UserOperateModel(12, "1000012", "測試12", "0", "15666666666", "ry@qq.com", 120.0, "1"));
        users.put(13, new UserOperateModel(13, "1000013", "測試13", "1", "15666666666", "ry@qq.com", 380.0, "1"));
        users.put(14, new UserOperateModel(14, "1000014", "測試14", "0", "15666666666", "ry@qq.com", 280.0, "1"));
        users.put(15, new UserOperateModel(15, "1000015", "測試15", "0", "15666666666", "ry@qq.com", 570.0, "1"));
        users.put(16, new UserOperateModel(16, "1000016", "測試16", "1", "15666666666", "ry@qq.com", 260.0, "1"));
        users.put(17, new UserOperateModel(17, "1000017", "測試17", "1", "15666666666", "ry@qq.com", 210.0, "1"));
        users.put(18, new UserOperateModel(18, "1000018", "測試18", "1", "15666666666", "ry@qq.com", 340.0, "1"));
        users.put(19, new UserOperateModel(19, "1000019", "測試19", "1", "15666666666", "ry@qq.com", 160.0, "1"));
        users.put(20, new UserOperateModel(20, "1000020", "測試20", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.put(21, new UserOperateModel(21, "1000021", "測試21", "1", "15666666666", "ry@qq.com", 120.0, "1"));
        users.put(22, new UserOperateModel(22, "1000022", "測試22", "1", "15666666666", "ry@qq.com", 130.0, "1"));
        users.put(23, new UserOperateModel(23, "1000023", "測試23", "1", "15666666666", "ry@qq.com", 490.0, "1"));
        users.put(24, new UserOperateModel(24, "1000024", "測試24", "1", "15666666666", "ry@qq.com", 570.0, "1"));
        users.put(25, new UserOperateModel(25, "1000025", "測試25", "1", "15666666666", "ry@qq.com", 250.0, "1"));
        users.put(26, new UserOperateModel(26, "1000026", "測試26", "1", "15666666666", "ry@qq.com", 250.0, "1"));
    }

    /**
     * 表格
     */
    @GetMapping("/table")
    public String table()
    {
        return prefix + "/table";
    }

    /**
     * 其他
     */
    @GetMapping("/other")
    public String other()
    {
        return prefix + "/other";
    }

    /**
     * 查詢數據
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserOperateModel userModel)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<UserOperateModel> userList = new ArrayList<UserOperateModel>(users.values());
        // 查詢條件過濾
        if (StringUtils.isNotEmpty(userModel.getSearchValue()))
        {
            userList.clear();
            for (Map.Entry<Integer, UserOperateModel> entry : users.entrySet())
            {
                if (entry.getValue().getUserName().equals(userModel.getSearchValue()))
                {
                    userList.add(entry.getValue());
                }
            }
        }
        else if (StringUtils.isNotEmpty(userModel.getUserName()))
        {
            userList.clear();
            for (Map.Entry<Integer, UserOperateModel> entry : users.entrySet())
            {
                if (entry.getValue().getUserName().equals(userModel.getUserName()))
                {
                    userList.add(entry.getValue());
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize())
        {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size())
        {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }

    /**
     * 新增用戶
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用戶
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserOperateModel user)
    {
        Integer userId = users.size() + 1;
        user.setUserId(userId);
        return AjaxResult.success(users.put(userId, user));
    }

    /**
     * 新增保存主子表信息
     */
    @PostMapping("/customer/add")
    @ResponseBody
    public AjaxResult addSave(CustomerModel customerModel)
    {
        System.out.println(customerModel.toString());
        return AjaxResult.success();
    }

    /**
     * 修改用戶
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Integer userId, ModelMap mmap)
    {
        mmap.put("user", users.get(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用戶
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserOperateModel user)
    {
        return AjaxResult.success(users.put(user.getUserId(), user));
    }

    /**
     * 導出
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserOperateModel user)
    {
        List<UserOperateModel> list = new ArrayList<UserOperateModel>(users.values());
        ExcelUtil<UserOperateModel> util = new ExcelUtil<UserOperateModel>(UserOperateModel.class);
        return util.exportExcel(list, "用戶數據");
    }

    /**
     * 下載模板
     */
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<UserOperateModel> util = new ExcelUtil<UserOperateModel>(UserOperateModel.class);
        return util.importTemplateExcel("用戶數據");
    }

    /**
     * 導入數據
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<UserOperateModel> util = new ExcelUtil<UserOperateModel>(UserOperateModel.class);
        List<UserOperateModel> userList = util.importExcel(file.getInputStream());
        String message = importUser(userList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 刪除用戶
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        Integer[] userIds = Convert.toIntArray(ids);
        for (Integer userId : userIds)
        {
            users.remove(userId);
        }
        return AjaxResult.success();
    }

    /**
     * 查看詳細
     */
    @GetMapping("/detail/{userId}")
    public String detail(@PathVariable("userId") Integer userId, ModelMap mmap)
    {
        mmap.put("user", users.get(userId));
        return prefix + "/detail";
    }

    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean()
    {
        users.clear();
        return success();
    }

    /**
     * 導入用戶數據
     *
     * @param userList 用戶數據列表
     * @param isUpdateSupport 是否更新支持，如果已存在，則進行更新數據
     * @return 結果
     */
    public String importUser(List<UserOperateModel> userList, Boolean isUpdateSupport)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("導入用戶數據不能為空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (UserOperateModel user : userList)
        {
            try
            {
                // 驗證是否存在這個用戶
                boolean userFlag = false;
                for (Map.Entry<Integer, UserOperateModel> entry : users.entrySet())
                {
                    if (entry.getValue().getUserName().equals(user.getUserName()))
                    {
                        userFlag = true;
                        break;
                    }
                }
                if (!userFlag)
                {
                    Integer userId = users.size() + 1;
                    user.setUserId(userId);
                    users.put(userId, user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、用戶 " + user.getUserName() + " 導入成功");
                }
                else if (isUpdateSupport)
                {
                    users.put(user.getUserId(), user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、用戶 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、用戶 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、賬號 " + user.getUserName() + " 導入失敗：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，導入失敗！共 " + failureNum + " 條數據格式不正確，錯誤如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，數據已全部導入成功！共 " + successNum + " 條，數據如下：");
        }
        return successMsg.toString();
    }
}
