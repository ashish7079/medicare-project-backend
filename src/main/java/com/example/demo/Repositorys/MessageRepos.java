package com.example.demo.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import com.example.demo.model.Messsage;

public interface MessageRepos
        extends JpaRepository<Messsage, Long> {


    @Query("""

    SELECT m

    FROM Messsage m

    WHERE

    (m.senderId = :senderId
    AND
    m.receiverId = :receiverId)

    OR

    (m.senderId = :receiverId
    AND
    m.receiverId = :senderId)

    ORDER BY m.id ASC

    """)

    List<Messsage> getChat(

            @Param("senderId")
            Long senderId,

            @Param("receiverId")
            Long receiverId
    );

    @Query("""

    SELECT DISTINCT m.senderId

    FROM Messsage m

    WHERE m.receiverId = :doctorId

    """)

    List<Long> findUsersByDoctorId(

            @Param("doctorId")
            Long doctorId
    );
}