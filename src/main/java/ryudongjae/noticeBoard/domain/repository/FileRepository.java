package ryudongjae.noticeBoard.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryudongjae.noticeBoard.domain.entity.File;

public interface FileRepository extends JpaRepository<File ,Long> {
}
