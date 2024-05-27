package com.example.marketproject.repo;

import com.example.marketproject.entity.Attachment;
import com.example.marketproject.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {


}
