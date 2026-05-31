package com.TaskManagementTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool.Entity.EmailLog;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long>{

}
