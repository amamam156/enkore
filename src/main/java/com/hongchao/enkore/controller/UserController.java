package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.User;
import com.hongchao.enkore.service.UserService;
import com.hongchao.enkore.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String from;

    @PostMapping("/sendMsg")
    private R<String> sendMsg(@RequestBody User user, HttpSession session)
    {
        //        这里用qq邮箱去发送验证码
        //获取到前端提交过来的qq号
        String email = user.getEmail();
        //这里工具类判是否为空
        if (StringUtils.isNotEmpty(email))
        {
            //            这里用到生成验证码的工具类
            String code = ValidateCodeUtils.generateValidateCode(4).toString();//生成四位的验证码
            log.info("code={}", code);
            //            构建一个邮件的对象
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //          设置邮件发件者
            simpleMailMessage.setFrom(from);
            //            设置邮件接受者
            simpleMailMessage.setTo(email);
            //            设置有纪念的主题
            simpleMailMessage.setSubject("登录验证码");
            //            设置邮件的征文
            String text = "[瑞吉外卖] 您的验证码为" + code + "请勿泄露";
            simpleMailMessage.setText(text);
            //将生成的验证码保存到Session
            //            将我们生成的手机号和验证码放到session里面，我们后面用户填入验证码之后，我们验证的时候就从这里去取然后进行比对
            //这里我们需要一个异常捕获
            session.setAttribute(email, code);
            //            return R.success("手机验证码短信发送成功");
            try
            {
                // javaMailSender.send(simpleMailMessage);
                log.info(code);
                return R.success("手机验证码短信发送成功");
            } catch (MailException e)
            {
                e.printStackTrace();
            }

        }
        return R.error("手机验证码发送失败");

    }

    //    移动应用登录端
    @PostMapping("/login")
    //    这里使用map来接收前端传过来的值
    private R<User> login(@RequestBody Map map, HttpSession session)
    {
        log.info(map.toString());
        //        使用map来接收参数,接收键值参数、
        //        编写处理逻辑
        //        获取到手机号
        //        获取到验证码
        //        从Session中获取到保存的验证码
        //     将session中获取到的验证码和前端提交过来的验证码进行比较，这样就可以实现一个验证的方式
        //        比对页面提交的验证码和session中
        //判断当前的手机号在数据库查询是否有记录，如果没有记录，说明是一个新的用户，然后自动将这个手机号进行注册
        String email = map.get("email").toString();
        String code = map.get("code").toString();
        //获取session中phone字段对应的验证码
        Object codeInSession = session.getAttribute(email);
        //        下面进行比对
        if (codeInSession != null && codeInSession.equals(code))
        {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            //            在表中根据号码来查询是否存在该邮箱用户
            userLambdaQueryWrapper.eq(User::getEmail, email);
            User user = userService.getOne(userLambdaQueryWrapper);
            if (user == null)
            {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setEmail(email);
                user.setStatus(1);
                userService.save(user);
            }
            //            这里我们将user存储进去，后面各项操作，我们会用，其中拦截器那边会判断用户是否登录，所以我们将这个存储进去，
            session.setAttribute("user", user.getId());

            return R.success(user);
        }
        return R.error("验证失败");
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session)
    {
        //清除session中的id。
        session.removeAttribute("user");
        return R.success("退出登录成功");
    }

}
