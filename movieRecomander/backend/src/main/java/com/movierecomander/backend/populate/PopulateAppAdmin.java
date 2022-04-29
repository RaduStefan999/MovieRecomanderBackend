package com.movierecomander.backend.populate;

import com.movierecomander.backend.users.admin.AppAdmin;
import com.movierecomander.backend.users.admin.AppAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

public class PopulateAppAdmin implements ApplicationRunner {

    private final AppAdminRepository appAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PopulateAppAdmin( AppAdminRepository appAdminRepository) {
        this.appAdminRepository = appAdminRepository;
    }

    public void run(ApplicationArguments args) {

        if (Arrays.stream(args.getSourceArgs()).anyMatch(argument -> argument.equals("populate")))
        {
            AppAdmin admin1=new AppAdmin(111L,"ionescuandrei@gmail.com","Ionescu Andrei",
                     passwordEncoder.encode("ContAdmin1"));
            AppAdmin admin2=new AppAdmin(112L,"popescualexandra21@gmail.com","Popescu Alexandra",
                    passwordEncoder.encode("ContAdmin2"));
            AppAdmin admin3=new AppAdmin(113L,"georgescudaniel13@gmail.com","Georgescu Daniel",
                    passwordEncoder.encode("ContAdmin3"));

            appAdminRepository.save(admin1);
            appAdminRepository.save(admin2);
            appAdminRepository.save(admin3);

        }
    }
}
