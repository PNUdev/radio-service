package com.pnu.dev.radioserviceapi;

import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

// Class can be used to setup test db on local machine to start development
@SpringBootTest
class DbSetupUtil {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    void programsDbSetup() {

        // setup db for programs
        IntStream.range(0, 20)
                .mapToObj(idx -> Program.builder()
                        .imageUrl("https://www.somagnews.com/wp-content/uploads/2020/04/75-e1586981465263.png")
                        .title("Title " + idx)
                        .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                                "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint" +
                                " occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit" +
                                " anim id est laborum.")
                        .build())
                .forEach(program -> programRepository.save(program));

    }

}
