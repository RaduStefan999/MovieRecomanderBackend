package com.movierecomander.backend.populate;

import com.movierecomander.backend.users.admin.AppAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;

public class PopulateAppAdmin implements ApplicationRunner {

    private final AppAdminRepository appAdminRepository;

    @Autowired
    public PopulateAppAdmin( AppAdminRepository appAdminRepository) {
        this.appAdminRepository = appAdminRepository;
    }

    public void run(ApplicationArguments args) {

        if (Arrays.stream(args.getSourceArgs()).anyMatch(argument -> argument.equals("populate")))
        {

        }
    }
}
