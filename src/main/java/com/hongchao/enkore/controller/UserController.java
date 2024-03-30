package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.web.bind.annotation.*;

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

        // Get the email submitted by the front end
        String email = user.getEmail();
        // Is it empty?
        if (StringUtils.isNotEmpty(email))
        {
            // Generate verification code
            String code = ValidateCodeUtils.generateValidateCode(4).toString();//Generate a four-digit verification code
            log.info("code={}", code);
            //Construct an email object
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //Set the email sender
            simpleMailMessage.setFrom(from);
            //Set email recipients
            simpleMailMessage.setTo(email);
            //Set a commemorative theme
            simpleMailMessage.setSubject("Login verification code");
            //Set up the email solicitation
            String text = "[Enkore Karaoke] Your verification code is" + code + "Do not disclose";
            simpleMailMessage.setText(text);

            session.setAttribute(email, code);
            // return R.success("Mobile phone verification code SMS sent successfully");
            try
            {
                // javaMailSender.send(simpleMailMessage);
                log.info(code);
                return R.success("verification code sent successful");
            } catch (MailException e)
            {
                e.printStackTrace();
            }

        }
        return R.error("Failed to send verification code");

    }

    //Mobile application login terminal
    @PostMapping("/login")
    // Use map here to receive the value passed by the front end
    private R<User> login(@RequestBody Map map, HttpSession session)
    {
        log.info(map.toString());

        //Determine whether the current mobile phone number is recorded in the database query. If there is no record, it means that it is a new user, and then the mobile phone number will be automatically registered.
        String email = map.get("email").toString();
        String code = map.get("code").toString();
        //Get the verification code corresponding to the phone field in the session
        Object codeInSession = session.getAttribute(email);
        // Compare below
        if (codeInSession != null && codeInSession.equals(code))
        {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // Query whether the email user exists based on the number in the table
            userLambdaQueryWrapper.eq(User::getEmail, email);
            User user = userService.getOne(userLambdaQueryWrapper);
            if (user == null)
            {
                //Determine whether the user corresponding to the current mobile phone number is a new user. If it is a new user, the registration will be automatically completed.
                user = new User();
                user.setEmail(email);
                user.setStatus(1);
                userService.save(user);
            }
            // Here we store the user, which we will use for subsequent operations. The interceptor will determine whether the user is logged in, so we store this in.
            session.setAttribute("user", user.getId());

            return R.success(user);
        }
        return R.error("Verification failed");
    }

   // logout
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session)
    {
        //Clear the id in the session.
        session.removeAttribute("user");
        return R.success("Logout successful");
    }

}