package com.aizwal.worker.repository;

import com.aizwal.worker.model.DocumentMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends MongoRepository<DocumentMetadata, String> {
}
