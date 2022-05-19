package com.movierecommender.backend.populate;

import com.movierecommender.backend.users.admin.AppAdmin;
import com.movierecommender.backend.users.admin.AppAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PopulateAppAdmin implements ApplicationRunner {

    private final AppAdminRepository appAdminRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PopulateAppAdmin( AppAdminRepository appAdminRepository, PasswordEncoder passwordEncoder) {
        this.appAdminRepository = appAdminRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void run(ApplicationArguments args) {

        if (Arrays.stream(args.getSourceArgs()).anyMatch(argument -> argument.equals("populate")))
        {
            AppAdmin admin1 = new AppAdmin("ionescuandrei@gmail.com","Ionescu Andrei",
                     passwordEncoder.encode("ContAdmin1"));
            AppAdmin admin2 = new AppAdmin("popescualexandra21@gmail.com","Popescu Alexandra",
                    passwordEncoder.encode("ContAdmin2"));
            AppAdmin admin3 = new AppAdmin("georgescudaniel13@gmail.com","Georgescu Daniel",
                    passwordEncoder.encode("ContAdmin3"));

            appAdminRepository.save(admin1);
            appAdminRepository.save(admin2);
            appAdminRepository.save(admin3);

        }
    }
}
