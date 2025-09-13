package com.hotelmanagement.repository;

import com.hotelmanagement.entity.UserRightsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRightsDataRepository extends JpaRepository<UserRightsData, String> {
    
    List<UserRightsData> findByUserId(String userId);
    
    List<UserRightsData> findByModuleName(String moduleName);
    
    List<UserRightsData> findByPermissionType(String permissionType);
    
    @Query("SELECT urd FROM UserRightsData urd WHERE urd.userId = :userId AND urd.moduleName = :moduleName")
    Optional<UserRightsData> findByUserIdAndModuleName(@Param("userId") String userId, 
                                                       @Param("moduleName") String moduleName);
    
    @Query("SELECT urd FROM UserRightsData urd WHERE urd.userId = :userId AND urd.permissionType IN ('read', 'write')")
    List<UserRightsData> findUserPermissions(@Param("userId") String userId);
    
    @Query("SELECT urd FROM UserRightsData urd WHERE urd.userId = :userId AND urd.moduleName = :moduleName AND urd.permissionType = :permissionType")
    Optional<UserRightsData> findByUserIdAndModuleNameAndPermissionType(@Param("userId") String userId, 
                                                                        @Param("moduleName") String moduleName, 
                                                                        @Param("permissionType") String permissionType);
    
    @Query("SELECT DISTINCT urd.moduleName FROM UserRightsData urd WHERE urd.userId = :userId AND urd.permissionType != 'none'")
    List<String> findAccessibleModulesByUserId(@Param("userId") String userId);
}
