package ryudongjae.noticeBoard.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryudongjae.noticeBoard.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
//repository는 데이터 조작을 담당하며 ,JpaRepository를 상속 받습니다.
// JpaRepository의 값은 매핑할 entity와 Id의 타입입니다.