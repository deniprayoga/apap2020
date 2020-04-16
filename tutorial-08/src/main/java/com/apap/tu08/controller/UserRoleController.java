package com.apap.tu08.controller;

import com.apap.tu08.model.PasswordModel;
import com.apap.tu08.model.UserRoleModel;
import com.apap.tu08.repository.UserRoleDB;
import com.apap.tu08.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @Autowired
    private UserRoleDB userRoleDB;

    private static Pattern passNumericPatern = Pattern.compile("[0-9]");
    private static Pattern passAlphabetPattern = Pattern.compile("[a-zA-Z]");

    public static boolean checkAlphabetNumeric(String s) {
        return passAlphabetPattern.matcher(s).find()
                && passNumericPatern.matcher(s).find();
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
        if (user.getPassword().length() < 8) {
            model.addAttribute("msg", "Panjang password minimal 8 karakter");
        } else {
            if (checkAlphabetNumeric(user.getPassword())) {
                userService.addUser(user);
                model.addAttribute("msg", "Tambah user berhasil");
            } else {
                model.addAttribute("msg", "Password harus mengandung kombinasi " +
                        "huruf dan angka");
            }
        }
        return "home";
    }

    @RequestMapping(value = "/updatePass", method = RequestMethod.POST)
    private String updatePass(@ModelAttribute PasswordModel passwordModel, Principal principal, Model model) {
        UserRoleModel updateUser = userRoleDB.findByUsername(principal.getName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(passwordModel.getOldPass(), updateUser.getPassword())
                && passwordModel.getNewPass().equals(passwordModel.getConfirmPassword())
                && checkAlphabetNumeric(passwordModel.getNewPass())
                && (passwordModel.getNewPass().length() >= 8)) {

            UserRoleModel user = new UserRoleModel();
            user.setId(updateUser.getId());
            user.setUsername(updateUser.getUsername());
            user.setRole(updateUser.getRole());
            user.setPassword(passwordModel.getNewPass());
            userService.addUser(user);

            model.addAttribute("msgPass", "Password berhasil diubah");
        } else {
            if (passwordEncoder.matches(passwordModel.getOldPass(), updateUser.getPassword())) {
                model.addAttribute("msgPass", "Password salah");
            } else if (passwordModel.getNewPass().equals(passwordModel.getConfirmPassword())) {
                model.addAttribute("msgPass", "Password tidak cocok");
            } else if (checkAlphabetNumeric(passwordModel.getNewPass())) {
                model.addAttribute("msgPass", "Password harus terdiri dari kombinasi " +
                        "huruf dan angka");
            } else if (passwordModel.getNewPass().length() >= 8) {
                model.addAttribute("msgPass", "Panjang password baru minimal 8 karakter");
            }
        }
        return "home";
    }

}
