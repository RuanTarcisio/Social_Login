package io.github.ruantarcisio.backend.s3.repository;

import io.github.ruantarcisio.backend.s3.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

}
