package com.pnu.dev.radioserviceapi.repository;

import com.pnu.dev.radioserviceapi.mongo.Program;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramRepository extends MongoRepository<Program, String> {

}
