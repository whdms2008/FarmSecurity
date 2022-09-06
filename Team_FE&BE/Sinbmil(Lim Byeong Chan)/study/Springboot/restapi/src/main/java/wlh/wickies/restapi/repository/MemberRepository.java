package wlh.wickies.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wlh.wickies.restapi.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> { }