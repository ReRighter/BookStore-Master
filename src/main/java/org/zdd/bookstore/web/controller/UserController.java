package org.zdd.bookstore.web.controller;

import org.zdd.bookstore.common.pojo.BSResult;
import org.zdd.bookstore.common.utils.BSResultUtil;
import org.zdd.bookstore.common.utils.IpUntils;
import org.zdd.bookstore.common.utils.OperLogAnnotation;
import org.zdd.bookstore.model.entity.LoginRecord;
import org.zdd.bookstore.model.entity.LogoutRecord;
import org.zdd.bookstore.model.entity.Store;
import org.zdd.bookstore.model.entity.User;
import org.zdd.bookstore.model.service.ILoginRecordService;
import org.zdd.bookstore.model.service.IStoreService;
import org.zdd.bookstore.model.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zdd.bookstore.model.service.impl.LogoutRecordImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;


    @Autowired
    private IStoreService storeService;
    @Autowired
    private ILoginRecordService loginRecordService;
    @Autowired
    private LogoutRecordImpl logoutRecord;

    private final String USERNAME_PASSWORD_NOT_MATCH = "用户名或密码错误";

    private final String USERNAME_CANNOT_NULL = "用户名不能为空";

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, Model model) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "login";
        }
        //未认证的用户
        Subject userSubject = SecurityUtils.getSubject();
        if (!userSubject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            token.setRememberMe(false);//禁止记住我功能
            try {

                //登录成功
                userSubject.login(token);
                User loginUser = (User) userSubject.getPrincipal();
                request.getSession().setAttribute("loginUser", loginUser);
                Store store = storeService.findStoreByUserId(loginUser.getUserId());
                request.getSession().setAttribute("loginStore", store);
                LoginRecord record = new LoginRecord();

                String ip = IpUntils.getClientIpAddr(request);
                record.setLoginIp(ip);
                record.setUseId(loginUser.getUserId());
                loginRecordService.saveRecord(record);

                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/";
                if (savedRequest != null) {
                    url = savedRequest.getRequestUrl();
                    if(url.contains(request.getContextPath())){
                        url = url.replace(request.getContextPath(),"");
                    }
                }
                if(StringUtils.isEmpty(url) || url.equals("/favicon.ico")){
                    url = "/";
                }

                return "redirect:" + url;

            } catch (UnknownAccountException | IncorrectCredentialsException uae) {
                model.addAttribute("loginMsg", USERNAME_PASSWORD_NOT_MATCH);
                return "login";
            } catch (LockedAccountException lae) {
                model.addAttribute("loginMsg", "账户已被冻结！");
                return "login";
            } catch (AuthenticationException ae) {
                model.addAttribute("loginMsg", "登录失败！");
                return "login";
            }

        } else {
            //用户已经登录
            return "redirect:/index";
        }

    }

    @RequestMapping("/info")
    public String personInfo(){
        return "user_info";
    }

    //shiro框架帮我们注销
    @RequestMapping("/logout")
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public String logout(HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println("test:"+user.getUsername()+user.getUserId());
        LogoutRecord record = new LogoutRecord();
        record.setLogoutIp(IpUntils.getClientIpAddr(request));
        record.setUserId(user.getUserId());
        logoutRecord.saveRecord(record);
        subject.logout();
        return "redirect:/page/login";
    }

    /**
     * 注册 检验用户名是否存在
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkUserExist")
    @ResponseBody
    public BSResult checkUserExist(String username) {
        if (StringUtils.isEmpty(username)) {
            return BSResultUtil.build(200, USERNAME_CANNOT_NULL, false);
        }

        return userService.checkUserExistByUsername(username);
    }

    /**
     * 注册，发激活邮箱
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Model model) {

        BSResult isExist = checkUserExist(user.getUsername());

        if ((Boolean) isExist.getData()) {

            BSResult bsResult = userService.saveUser(user);

            User userNotActive = (User) bsResult.getData();
            model.addAttribute("username", user.getUsername());
            return "register_success";
        } else {

            //用户名已经存在，不能注册
            model.addAttribute("registerError", isExist.getMessage());
            return "register";
        }

    }

    @RequestMapping("/active")
    public String activeUser(String activeCode, Model model) {
        BSResult bsResult = userService.activeUser(activeCode);
        if (!StringUtils.isEmpty(bsResult.getData())) {
            model.addAttribute("username", bsResult.getData());
            return "active_success";
        } else {
            model.addAttribute("failMessage", bsResult.getMessage());
            return "fail";
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    @OperLogAnnotation(operType = "userUpdate-")
    public BSResult updateUser(User user, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        loginUser.setNickname(user.getNickname());
        loginUser.setLocation(user.getLocation());
        loginUser.setDetailAddress(user.getDetailAddress());
        loginUser.setGender(user.getGender());
        loginUser.setUpdated(new Date());
        loginUser.setPhone(user.getPhone());
        loginUser.setIdentity(user.getIdentity());
        loginUser.setPhone(user.getPhone());
        BSResult bsResult = userService.updateUser(loginUser);
        session.setAttribute("loginUser", loginUser);
        return bsResult;
    }

    @RequestMapping("/password/{userId}")
    @ResponseBody
    public BSResult changePassword(@PathVariable("userId") int userId,String oldPassword,String newPassword){
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)){
            return BSResultUtil.build(400, "密码不能为空");
        }
        return userService.compareAndChange(userId,oldPassword,newPassword);
    }

}
