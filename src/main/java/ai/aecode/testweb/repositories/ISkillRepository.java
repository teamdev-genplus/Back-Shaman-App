package ai.aecode.testweb.repositories;

import ai.aecode.testweb.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Integer> {

    @Query("SELECT s FROM Skill s WHERE s.skill_name=:skillName")
    Skill findBySkillName(String skillName);
}
