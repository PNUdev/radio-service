package com.pnu.dev.radioserviceapi;

import com.pnu.dev.radioserviceapi.mongo.Program;
import com.pnu.dev.radioserviceapi.repository.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class RadioServiceApiApplicationTests {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    void programsDbSetup() {

        // setup db for programs
        IntStream.range(0, 20)
                .mapToObj(idx -> Program.builder()
                        .image("https://www.somagnews.com/wp-content/uploads/2020/04/75-e1586981465263.png")
                        .title("Title " + idx)
                        .description("some description " + idx)
                        .build())
                .forEach(program -> programRepository.save(program));

    }

}
